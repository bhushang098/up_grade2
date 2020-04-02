package my.upgrade007.upgrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upgrade.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username, userPass, userEmail;
    private Button regRutton, goLoginBtn;
    private TextView goLogin;
    ImageView closeImage;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    NetworkOperation checkNet;
    CheckBox checkBox;
    Dialog oneTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        oneTimeDialog = new Dialog(this);
        checkNet = new NetworkOperation();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firsStart = sharedPreferences.getBoolean("firstStartOfReg", true);
        if (firsStart) {
            showOneTimeDialog();
        }

        setupUi(); //This method will Find All the widges And Assign To Variabels

        firebaseAuth = FirebaseAuth.getInstance();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else
                    userPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {  //Listener Of Already User? LogIn
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        regRutton.setOnClickListener(new View.OnClickListener() {  //Listiner of REgister Button
            @Override
            public void onClick(View v) {

                if (validate()) {
                    if (checkNet.checknetConnection(RegistrationActivity.this)) {
                        String user_Email = userEmail.getText().toString().trim();
                        String password = userPass.getText().toString().trim();
                        progressDialog = new ProgressDialog(RegistrationActivity.this);
                        progressDialog.setTitle("Registering");
                        progressDialog.setMessage("Checking Details....");
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(user_Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegistrationActivity.this, MainPage.class);
                                    startActivity(i);
                                    progressDialog.hide();
                                    finish();
                                } else {
                                    progressDialog.hide();
                                    Toast.makeText(RegistrationActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else
                        Toast.makeText(RegistrationActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Boolean validate() {  // Checks All filed Have Been Entered Correctly
        Boolean result = false;

        String name = username.getText().toString();
        String password = userPass.getText().toString();
        String email = userEmail.getText().toString();
        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    public void setupUi() {
        checkBox = findViewById(R.id.checkBoxOfRegister);
        goLogin = findViewById(R.id.tvGoLogin);
        username = findViewById(R.id.etUserNmaeReg);
        userPass = findViewById(R.id.etPassReg);
        userEmail = findViewById(R.id.etMailReg);
        regRutton = findViewById(R.id.btnRegister);
    }

    public void showOneTimeDialog() {
        oneTimeDialog.setContentView(R.layout.custom_popup_of_reg_activity);
        goLoginBtn = oneTimeDialog.findViewById(R.id.btnGoLoginDialog);
        closeImage = oneTimeDialog.findViewById(R.id.btncloseDialogOfGoLogin);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTimeDialog.dismiss();
            }
        });

        goLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
               startActivity(i);
               finish();
            }
        });
        oneTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.color.colorAccent));
        oneTimeDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStartOfReg", false);
        editor.apply();

    }
}

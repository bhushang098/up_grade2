package my.upgrade007.upgrade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upgrade.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText loginMail, loginPass;
    private TextView goRegister, forgotPass;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;
    TextView goToresetpass;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener mauthStateListener;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBar;
    CheckBox checkBox;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mauthStateListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUi();
        final NetworkOperation checkNet = new NetworkOperation();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        final boolean firsStart = sharedPreferences.getBoolean("firstIntent",true);

        firebaseAuth = FirebaseAuth.getInstance();

        mauthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (!firsStart) {
                        Intent i = new Intent(LoginActivity.this, MainPage.class);
                        startActivity(i);
                        finish();
                    }
                }
                SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firstIntent",false);
                editor.apply();
            }
        };

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                    loginPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmailflogin = loginMail.getText().toString();
                String userpassflogin = loginPass.getText().toString();

                if (userEmailflogin.isEmpty() || userpassflogin.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter All Details", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Validating");
                    progressDialog.setMessage("Checking details.. Please Wait");
                    progressDialog.show();
                    loginIn(userEmailflogin, userpassflogin, checkNet);
                }
            }
        });

        goRegister.setOnClickListener(new View.OnClickListener() { // Listener to Jump In Registration Activity (For New Users)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });
        goToresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    private void loginIn(final String userEmail, final String userPass, final NetworkOperation checkNet) {
        if (checkNet.checknetConnection(LoginActivity.this)) {

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainPage.class);
                        startActivity(intent);
                        progressDialog.hide();
                        finish();
                    } else {
                        if (userEmail.isEmpty() || userPass.isEmpty()) {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Error Validating", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            progressDialog.hide();
            Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void setupUi() {
        checkBox = findViewById(R.id.cbShowPAss);
        progressBar = findViewById(R.id.pbOFLogin);
        goRegister = findViewById(R.id.tvRegister);
        forgotPass = findViewById(R.id.tvforgotPassword);
        loginMail = findViewById(R.id.etMailLogin);
        loginPass = findViewById(R.id.etPassLogin);
        loginBtn = findViewById(R.id.btnlogin);
        goToresetpass = findViewById(R.id.tvforgotPassword);

    }

}

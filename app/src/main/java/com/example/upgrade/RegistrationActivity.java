package com.example.upgrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username, userPass, userEmail;
    private Button regRutton;
    private TextView goLogin;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setupUi(); //This method will Find All the widges And Assign To Variabels

        firebaseAuth = FirebaseAuth.getInstance();

        goLogin.setOnClickListener(new View.OnClickListener() {  //Listener Of Already User? LogIn
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        regRutton.setOnClickListener(new View.OnClickListener() {  //Listiner of REgister Button
            @Override
            public void onClick(View v) {

                if (validate()) {
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
                                progressDialog.hide();
                                Toast.makeText(RegistrationActivity.this, "Registration Succesfull...Now LogIn", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                            }else {
                                progressDialog.hide();
                                Toast.makeText(RegistrationActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
            Toast.makeText(RegistrationActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;

    }

    public void setupUi() {
        goLogin = findViewById(R.id.tvGoLogin);
        username = findViewById(R.id.etUserNmaeReg);
        userPass = findViewById(R.id.etPassReg);
        userEmail = findViewById(R.id.etMailReg);
        regRutton = findViewById(R.id.btnRegister);
    }
}

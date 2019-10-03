package com.example.upgrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private EditText loginMail, loginPass;
    private TextView goRegister, forgotPass;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;
    TextView goToresetpass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUi();
        firebaseAuth = FirebaseAuth.getInstance();

        final String userEmail = loginMail.getText().toString();
        final String userpass = loginPass.getText().toString();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmailflogin = loginMail.getText().toString();
                String userpassflogin = loginPass.getText().toString();

                if (userEmailflogin.isEmpty() || userpassflogin.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Enter All Details",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Validating");
                    progressDialog.setMessage("Checking details.. Please Wait");
                    progressDialog.show();
                    loginIn(userEmailflogin,userpassflogin);
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


    public void setupUi() {
        goRegister = findViewById(R.id.tvRegister);
        forgotPass = findViewById(R.id.tvforgotPassword);
        loginMail = findViewById(R.id.etMailLogin);
        loginPass = findViewById(R.id.etPassLogin);
        loginBtn = findViewById(R.id.btnlogin);
        goToresetpass = findViewById(R.id.tvforgotPassword);

    }

    private void loginIn(final String userEmail, final String userPass) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.hide();
                    Intent intent = new Intent(LoginActivity.this,MainPage.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (userEmail.isEmpty() || userPass.isEmpty()) {
                        progressDialog.hide();
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.hide();
                        Toast.makeText(LoginActivity.this, "Error validating", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

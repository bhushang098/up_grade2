package com.example.upgrade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import androidx.cardview.widget.CardView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText loginMail, loginPass;
    private TextView goRegister, forgotPass;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth;
    TextView goToresetpass;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener mauthStateListener;
    CardView cvGLogion;
    GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1;
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
        firebaseAuth = FirebaseAuth.getInstance();

        mauthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent i = new Intent(LoginActivity.this, MainPage.class);
                    startActivity(i);
                }
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

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        cvGLogion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNet.checknetConnection(LoginActivity.this)) {
                    progressBar.setVisibility(View.VISIBLE);
                    signIn();
                } else
                    Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
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
                        progressDialog.hide();
                        Intent intent = new Intent(LoginActivity.this, MainPage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (userEmail.isEmpty() || userPass.isEmpty()) {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Error Validating", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            progressDialog.hide();
            Toast.makeText(LoginActivity.this, "Check InterNet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Toast.makeText(LoginActivity.this, "DEta Got Of Acct", Toast.LENGTH_SHORT).show();
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                String mess = e.toString();
                Log.d("TAG>>>>>>>", "FIReBAse EXCeption " +mess);
                Toast.makeText(LoginActivity.this, mess, Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        Toast.makeText(LoginActivity.this, "in firebaase auth method", Toast.LENGTH_SHORT).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(LoginActivity.this, "Task Succesfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainPage.class);
                            startActivity(intent);
                            finish();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Error Validating", Toast.LENGTH_SHORT).show();
                           // Snackbar.make(findViewById(R.id.reflayoutJob), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void setupUi() {
        checkBox = findViewById(R.id.cbShowPAss);
        progressBar = findViewById(R.id.pbOFLogin);
        cvGLogion = findViewById(R.id.cvGLogin);
        goRegister = findViewById(R.id.tvRegister);
        forgotPass = findViewById(R.id.tvforgotPassword);
        loginMail = findViewById(R.id.etMailLogin);
        loginPass = findViewById(R.id.etPassLogin);
        loginBtn = findViewById(R.id.btnlogin);
        goToresetpass = findViewById(R.id.tvforgotPassword);

    }

}

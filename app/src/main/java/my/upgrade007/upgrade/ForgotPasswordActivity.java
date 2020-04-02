package my.upgrade007.upgrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upgrade.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText tobeResetdmail;
    private Button getLink;
    private FirebaseAuth firebaseAuth;
    NetworkOperation checkNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tobeResetdmail = findViewById(R.id.etPassReset);
        getLink = findViewById(R.id.btnResetpass);
        firebaseAuth = FirebaseAuth.getInstance();
        checkNet = new NetworkOperation();

        getLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmailToReset = tobeResetdmail.getText().toString();

                if (userEmailToReset.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Kindly Give Your Registered E-mail id", Toast.LENGTH_SHORT).show();
                }else if (checkNet.checknetConnection(ForgotPasswordActivity.this)){
                    firebaseAuth.sendPasswordResetEmail(userEmailToReset).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Email sent Check Your InBox", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Error Sending E-mail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else
                    Toast.makeText(ForgotPasswordActivity.this,"Check Network Connection",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

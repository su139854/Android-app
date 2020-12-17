package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity2 extends AppCompatActivity {
    Button Submit;
    public EditText emailForgotten;
    com.google.firebase.auth.FirebaseAuth FirebaseAuth12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass2);
        Submit = findViewById(R.id.button4);
        emailForgotten = findViewById(R.id.editTextTextEmailAddress);
        FirebaseAuth12 = com.google.firebase.auth.FirebaseAuth.getInstance();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailForgotten.getText().toString();
                //Intent toBackLog = new Intent(ForgotPassActivity2.this, LoginActivity2.class);
                //startActivity(toBackLog);
                //FirebaseAuth12 = com.google.firebase.auth.FirebaseAuth.getInstance(); //made into element array

                FirebaseAuth12.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassActivity2.this, "Password sent to your email", Toast.LENGTH_LONG).show();
                                    Intent toBackLog = new Intent(ForgotPassActivity2.this, LoginActivity2.class);
                                    startActivity(toBackLog);
                                } else {
                                    Toast.makeText(ForgotPassActivity2.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }); //

            }
        });

    }

}
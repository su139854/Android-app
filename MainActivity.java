package com.example.login1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;

public class MainActivity extends AppCompatActivity
{
    //FirebaseApp.initializeApp();
    public EditText emailID, password;
    Button BSignUp;
    TextView TSignIn;
    com.google.firebase.auth.FirebaseAuth FirebaseAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth1 = com.google.firebase.auth.FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        BSignUp = findViewById(R.id.button);
        TSignIn = findViewById(R.id.textView3);
        BSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pass = password.getText().toString();
                if (email.isEmpty())
                {
                    emailID.setError("Please enter an email");
                    emailID.requestFocus();
                }
                else if (pass.isEmpty())
                {
                    password.setError("Please enter an email");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Email and Password are empty",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty()))
                {
                    FirebaseAuth1.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Sign Up was unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(MainActivity.this,MenuActivity2.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Error1",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //maybe fix this
        TSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity2.class);
                startActivity(i);


            }
        }); //Maybe delete

    }

}
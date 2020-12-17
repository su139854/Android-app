package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity2 extends AppCompatActivity {

    public EditText emailID, password;
    Button BSignIn;
    TextView TSignIn;
    TextView TForgotPass;
    com.google.firebase.auth.FirebaseAuth FirebaseAuth1;
    private FirebaseAuth.AuthStateListener ListenerFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        FirebaseAuth1 = com.google.firebase.auth.FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        BSignIn = findViewById(R.id.button);
        TSignIn = findViewById(R.id.textView3);
        TForgotPass = findViewById(R.id.textView6);


        ListenerFirebase = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser FirebaseUser1 = FirebaseAuth1.getCurrentUser();
                if(FirebaseUser1 != null)
                {
                    Toast.makeText(LoginActivity2.this,"Logged In",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity2.this,MenuActivity2.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity2.this,"Please Log In",Toast.LENGTH_SHORT).show();
                }

            }
        };
        BSignIn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity2.this,"Email and Password are empty",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty()))
                {
                    FirebaseAuth1.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity2.this,"Login Error",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent toMain = new  Intent(LoginActivity2.this,MenuActivity2.class);
                                startActivity(toMain);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity2.this,"Error",Toast.LENGTH_SHORT).show();
                }

            }
        });
        TSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sign_Up = new  Intent(LoginActivity2.this,MainActivity.class);//changed
                startActivity(Sign_Up);
            }
        });
        TForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDiet = new Intent(LoginActivity2.this, ForgotPassActivity2.class);
                startActivity(toDiet);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth1.addAuthStateListener(ListenerFirebase);
    }

}
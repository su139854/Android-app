package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vitals_view extends AppCompatActivity {
    TextView pulse, blood, gluc, chol, body;
    DatabaseReference reff;
    Button ret;
    FirebaseAuth off = FirebaseAuth.getInstance();
    String userEmail = off.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals_view);



        pulse = findViewById(R.id.Name);
        blood = findViewById(R.id.Age);
        gluc = findViewById(R.id.Height);
        chol = findViewById(R.id.Gender);
        body = findViewById(R.id.Weight);

        ret = findViewById(R.id.Return);
        String x= userEmail.replace(".", ",");
        reff = FirebaseDatabase.getInstance().getReference().child("User Info").child("Vitals");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String p = snapshot.child(x).child("pulse").getValue().toString();
                pulse.setText(p);
                String age = snapshot.child(x).child("blood").getValue().toString();
                blood.setText(age);
                String height = snapshot.child(x).child("glucose").getValue().toString();
                gluc.setText(height);
                String gender = snapshot.child(x).child("cholestrol").getValue().toString();
                chol.setText(gender);
                String weight = snapshot.child(x).child("body").getValue().toString();
                body.setText(weight);


                ret.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent toper = new Intent(Vitals_view.this, Vitals_main.class);
                        startActivity(toper);


                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
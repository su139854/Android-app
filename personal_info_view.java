package com.example.login1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class personal_info_view extends AppCompatActivity {

   TextView a, b, c, d, e, f;
   DatabaseReference reff;
   Button ret;
    FirebaseAuth off = FirebaseAuth.getInstance();
    String userEmail = off.getCurrentUser().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        a = findViewById(R.id.Name);
        b = findViewById(R.id.Age);
        c = findViewById(R.id.Height);
        d = findViewById(R.id.Gender);
        e = findViewById(R.id.Weight);
        f = findViewById(R.id.birthDate);
        ret = findViewById(R.id.Return);
        String x= userEmail.replace(".", ",");
        reff = FirebaseDatabase.getInstance().getReference().child("User Info").child("User");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(x).child("dname").getValue().toString();
                a.setText(name);
                String age = snapshot.child(x).child("age").getValue().toString();
                b.setText(age);
                String height = snapshot.child(x).child("height").getValue().toString();
                c.setText(height);
                String gender = snapshot.child(x).child("gender").getValue().toString();
                d.setText(gender);
                String weight = snapshot.child(x).child("weight").getValue().toString();
                e.setText(weight);
                String birth = snapshot.child(x).child("birth").getValue().toString();
                f.setText(birth);

                ret.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toper = new Intent(personal_info_view.this, personal_info_main.class);
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
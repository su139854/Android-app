package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDietActivity2 extends AppCompatActivity {

    String temp1 = null;
    String temp2 = null;
    String temp3 = null;
    Button Back;
    TextView a, b, c;
    DatabaseReference reff1;
    FirebaseAuth off = FirebaseAuth.getInstance();
    String userEmail1 = off.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diet2);
        Back = findViewById(R.id.button8);
        String Link1 = null;
        String Link2 = null;
        String Link3 = null;

        a = findViewById(R.id.textView7);
        b = findViewById(R.id.textView9);
        c = findViewById(R.id.textView10);

        String x= userEmail1.replace(".", ",");
        reff1 = FirebaseDatabase.getInstance().getReference().child("User Info").child("Diet");

        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String calCount1 = snapshot.child(x).child("calCount").getValue().toString();
                b.setText(calCount1);
                String foodName1 = snapshot.child(x).child("foodName").getValue().toString();
                a.setText(foodName1);
                String weight1 = snapshot.child(x).child("weight").getValue().toString();
                c.setText(weight1);



                Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toView = new Intent(ViewDietActivity2.this, DietActivity2.class);
                        startActivity(toView);
                    }

                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //String temp1 = null;
        //String temp2 = null;
        //String temp3 = null;

        //Bundle bundle=getIntent().getExtras();
        //if( getIntent().getExtras() != null)
        //{
        //Link1 =bundle.getString("FoodIntake");
        //Link2 =bundle.getString("Cal");
        //Link3 =bundle.getString("Weight");
        //if (Link1 != null)
        //{
        // temp1 = Link1;
        // temp2 = Link2;
        // temp3 = Link3;
        // }
        // }

        /*
        if( getIntent().getExtras() == null)
        {
            Link1 ="Not";
            Link2 ="Not";
            Link3 = "Not";
        }
        */



        //TextView textView =  findViewById(R.id.textView7);
        //TextView textView1 =  findViewById(R.id.textView9);
        //TextView textView2 =  findViewById(R.id.textView10);
        //textView.setText(temp1);
        //textView1.setText(temp2);
        //textView2.setText(temp3);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toView = new Intent(ViewDietActivity2.this, DietActivity2.class);
                startActivity(toView);
            }

        });



    }
}
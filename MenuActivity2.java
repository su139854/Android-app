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

public class MenuActivity2 extends AppCompatActivity {
    Button LogOut;
    Button BDiet;
    Button PersonalInfo;
    Button Medications;
    Button Search;
    Button Vitals;

    TextView welcomeText;

    FirebaseAuth FirebaseAuth1;
    private FirebaseAuth.AuthStateListener ListenerFirebase;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userEmail = auth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        // now that we're logged in, start monitoring service
        startService(new Intent(this, Monitoring.class));

        LogOut = findViewById(R.id.button3);
        BDiet = findViewById(R.id.button2);
        PersonalInfo = findViewById(R.id.button10);
        Medications = findViewById(R.id.button_search_med);
        Search = findViewById(R.id.button_search);
        Vitals = findViewById(R.id.buttonVitals);
        welcomeText = findViewById(R.id.welcomeText);

        welcomeText.setText("Logged in as: "+userEmail+".\n\nPlease make a selection.");

        /*
        String x= userEmail.replace(".", ",");
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User Info").child("User");

        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(x).child("name").getValue().toString();
                welcomeText.setText("Welcome, "+name+".\nPlease make a selection.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuActivity2.this, "Error.", Toast.LENGTH_SHORT).show();
            }
        });
         */

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toMain = new Intent(MenuActivity2.this, MainActivity.class);
                startActivity(toMain);
            }
        });
        BDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDiet = new Intent(MenuActivity2.this, DietActivity2.class);
                startActivity(toDiet);
            }
        });
        PersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPersonalInfo = new Intent(MenuActivity2.this, personal_info_main.class);
                startActivity(toPersonalInfo);
            }
        });
        Medications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMedications = new Intent(MenuActivity2.this, MedicationActivity.class);
                startActivity(toMedications);
            }
        });
        Search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent toSearch = new Intent(MenuActivity2.this, SearchActivity.class);
                startActivity(toSearch);
            }
        });
        Vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPersonalInfo = new Intent(MenuActivity2.this, Vitals_main.class);
                startActivity(toPersonalInfo);
            }
        });
    }
}
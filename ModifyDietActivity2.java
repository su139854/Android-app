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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyDietActivity2 extends AppCompatActivity {
    EditText FoodIntake;
    EditText Calories;
    EditText Weight;
    Button Save;
    DatabaseReference dbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_diet2);

        FoodIntake = (EditText) findViewById(R.id.editTextTextPersonName7);
        Calories = (EditText) findViewById(R.id.editTextTextPersonName8);
        Weight = (EditText) findViewById(R.id.editTextTextPersonName9);
        Save = findViewById(R.id.button7);

        dbr= FirebaseDatabase.getInstance().getReference().child("User Info");

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDietData();

                Toast.makeText(ModifyDietActivity2.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                Intent toDiet = new Intent(ModifyDietActivity2.this, DietActivity2.class);
                startActivity(toDiet);
            }

        });



    }

    public void sendDietData()
    {

        String textToPass = FoodIntake.getText().toString();
        String textToPass1 = Calories.getText().toString();
        String textToPass2 = Weight.getText().toString();

        DietData TempData = new DietData(textToPass,textToPass1,textToPass2);
        String userEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ",");

        dbr.child("Diet").child(userEmail).setValue(TempData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ModifyDietActivity2.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ModifyDietActivity2.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
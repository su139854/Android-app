package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class personal_Info extends AppCompatActivity {

    EditText userName, centi, ageText, weightText, genderText, Birthdate;
    Button yolo;


//    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbr;
    FirebaseAuth off = FirebaseAuth.getInstance();
    String userEmail = off.getCurrentUser().getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__info);


        centi = findViewById(R.id.inches);


        ageText = findViewById(R.id.ageText);


        weightText = findViewById(R.id.weightText);


        genderText = findViewById(R.id.genderText);


        Birthdate = findViewById(R.id.birthDate);








        yolo = findViewById(R.id.Save);

        userName = findViewById(R.id.Name_entry);

       // dbr = db.getReference("UserInfo");
        dbr = FirebaseDatabase.getInstance().getReference().child("User Info");

        yolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNamer = userName.getText().toString();
                String age = ageText.getText().toString();
                String centir = centi.getText().toString();
                String weightTextr = weightText.getText().toString();
                String genderTextr = genderText.getText().toString();
                String Birthdater = Birthdate.getText().toString();



                if(TextUtils.isEmpty(userNamer))
                {
                    Toast.makeText(personal_Info.this, "Name field incorrect", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(age))
                {
                    Toast.makeText(personal_Info.this, "Age field Incorrect", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(centir))
                {
                    Toast.makeText(personal_Info.this, "Height (inches) field Incorrect", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(weightTextr))
                {
                    Toast.makeText(personal_Info.this, "Weight field Incorrect", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(genderTextr))
                {
                    Toast.makeText(personal_Info.this, "Gender field Incorrect", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Birthdater))
                {
                    Toast.makeText(personal_Info.this, "Birthdate field Incorrect", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent tomenu = new Intent(personal_Info.this, personal_info_main.class);
                    startActivity(tomenu);
                    sendData();
                }

            }
        });
    }



    public void sendData()
    {

        String id = dbr.push().getKey();
        String userNameS = userName.getText().toString();
        int ages = Integer.parseInt(ageText.getText().toString());
        String birth = Birthdate.getText().toString();
        String gender = genderText.getText().toString();
        Double weight = Double.parseDouble(weightText.getText().toString());
        String cm = centi.getText().toString();

        Data user = new Data(userEmail,userNameS, weight, ages, birth, gender, cm);
        userEmail= userEmail.replace(".", ",");
        dbr.child("User").child(userEmail).setValue(user);

        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }
}

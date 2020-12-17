package com.example.login1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DietActivity2 extends AppCompatActivity {
    Button ModifyDiet;
    Button ViewDiet;
    Button BacktoMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet2);
        ModifyDiet = findViewById(R.id.button5);
        ViewDiet = findViewById(R.id.button6);
        BacktoMain = findViewById(R.id.backtoMain1);

        ModifyDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toModify = new Intent(DietActivity2.this, ModifyDietActivity2.class);
                startActivity(toModify);
            }
        });

        ViewDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toView = new Intent(DietActivity2.this, ViewDietActivity2.class);
                startActivity(toView);
            }

        });

        BacktoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toView = new Intent(DietActivity2.this, MenuActivity2.class);
                startActivity(toView);
            }

        });


    }
}
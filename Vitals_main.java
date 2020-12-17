package com.example.login1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Vitals_main extends AppCompatActivity {

    Button one, two, buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals_main);

        one = findViewById(R.id.VitalsInfo);
        two = findViewById(R.id.VitalEntry);
        buttonMenu = findViewById(R.id.button_menu);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent toPersonal = new Intent(Vitals_main.this, Vitals_view.class);
                startActivity(toPersonal);

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tomodify = new Intent(Vitals_main.this, Modify_vitals.class);
                startActivity(tomodify);

            }
        });
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainMenu = new Intent(Vitals_main.this, MenuActivity2.class);
                startActivity(toMainMenu);

            }
        });
    }
}
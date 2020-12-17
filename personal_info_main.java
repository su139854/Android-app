package com.example.login1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class personal_info_main extends AppCompatActivity {

    Button modify, see, menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_main);

        modify = findViewById(R.id.button6);
        see = findViewById(R.id.button7);
        menu = findViewById(R.id.button9);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent toPersonal = new Intent(personal_info_main.this, personal_Info.class);
                    startActivity(toPersonal);

            }
        });

        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomodify = new Intent(personal_info_main.this, personal_info_view.class);
                startActivity(tomodify);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomenu = new Intent(personal_info_main.this, MenuActivity2.class);
                startActivity(tomenu);
            }
        });






    }
}
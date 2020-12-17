package com.example.login1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {

    Button searchMedications;
    Button searchDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchMedications = findViewById(R.id.button_search_med);
        searchDoctors = findViewById(R.id.button_search_doctors);

        searchMedications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSearchMeds = new Intent(SearchActivity.this, SearchMedsActivity.class);
                startActivity(toSearchMeds);
            }
        });
        searchDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSearchUsers = new Intent(SearchActivity.this, SearchDocActivity.class);
                startActivity(toSearchUsers);
            }
        });
    }
}
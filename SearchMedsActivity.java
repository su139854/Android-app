package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class SearchMedsActivity extends AppCompatActivity {

    Button searchButton;
    TextView searchResultBox;
    EditText searchBox;
    String searchQuery;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference meds = db.collection("medications");


    public void doSearch(String medName) {
        searchResultBox = findViewById(R.id.searchResultMed);


        meds.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean hadFound = false;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> currMed = document.getData();
                                String id = document.getId();
                                String name = (String) currMed.get("name");
                                String conflicts = (String) currMed.get("conflicts");

                                if (medName.toLowerCase().equals(name.toLowerCase())) {
                                    Log.d("SEARCH", "There is a match: " + id + " " + name);
                                    searchResultBox.setText("There is a match for \""+ medName +"\"\n\nName: "+name+"\nConflicting Meds: "+conflicts);
                                    hadFound = true;
                                }

                            }
                            if(!hadFound)
                            {
                                Log.d("SEARCH", "No match for: " + medName);
                                searchResultBox.setText("No Match Found For: "+ medName);
                            }
                        } else {
                            Log.d("SEARCH", "There was issue connecting to database");
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meds);

        searchButton = findViewById(R.id.buttonDoMedSearch);
        searchBox = findViewById(R.id.searchBoxMed);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = searchBox.getText().toString();
                doSearch(searchQuery);
            }
        });

    }

}
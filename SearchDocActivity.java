package com.example.login1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SearchDocActivity extends AppCompatActivity {

    Button searchButton;
    TextView searchResultBox;
    EditText searchBox;
    String searchQuery;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference docs = db.collection("doctors");


    public void doSearch(String doctorName) {
        searchResultBox = findViewById(R.id.searchResultDoc);



        docs.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean hadFound = false;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> currDoc= document.getData();
                                String id = document.getId();
                                String name = (String) currDoc.get("name");
                                String email = (String) currDoc.get("email");

                                if(doctorName.toLowerCase().equals(name.toLowerCase()))
                                {
                                    Log.d("SEARCH","There is a match: "+id+" "+name+" "+email);
                                    searchResultBox.setText("There is a match for \""+ doctorName +"\"\n\nName: "+name+"\nEmail: "+email);
                                    hadFound = true;
                                }

                            }
                            if(!hadFound) {
                                Log.d("SEARCH", "No match for: " + doctorName);
                                searchResultBox.setText("No Match Found For: "+ doctorName);
                            }

                        } else {
                            Log.d("SEARCH","There was an issue connecting to the database.");
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);

        searchButton = findViewById(R.id.buttonDoDocSearch);
        searchBox = findViewById(R.id.searchBoxDoc);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = searchBox.getText().toString();
                doSearch(searchQuery);
            }
        });

    }
}
package com.example.login1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicationActivity extends AppCompatActivity {

    private static final String TAG = "MEDICATION";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);


        // initialize medlist
        ArrayList<Medication> medicationList = new ArrayList<Medication>();

        // populate list
        medicationList.add(new Medication("med1", "Lisinopril", "Aspirin,Iosartin"));
        medicationList.add(new Medication("med2", "Aspirin", "Lisinopril"));
        medicationList.add(new Medication("med3", "Atorvastatin", ""));
        medicationList.add(new Medication("med4", "Metformin", "Gatifloxacin"));
        medicationList.add(new Medication("med5", "Gatifloxacin", "Metformin"));

        // display list9/8
        MedButtonAdapter medAdapter = new MedButtonAdapter(this, medicationList);
        ListView listView = (ListView) findViewById(R.id.listview_meds);
        listView.setAdapter(medAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent toChosenMed = new Intent(MedicationActivity.this, ChosenMedActivity.class);

                // pass data to activity
                Medication currMed = medAdapter.getItem(i);
                toChosenMed.putExtra("MED_ID", currMed.GetId());
                toChosenMed.putExtra("MED_NAME", currMed.GetName());
                startActivity(toChosenMed);
            }
        });

    }
}
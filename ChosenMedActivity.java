package com.example.login1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ChosenMedActivity extends AppCompatActivity {

    private static final String TAG = "CHOSEN_MED";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView tvMedName;
    private TextView tvMedPrescribed;
    private TextView tvMedTaken;
    private TextView tvScheduledTime;
    private TextView tvExpirationDate;

    private Button button_prescribe;
    private Button button_takemed;
    private Button button_renewmed;
    private Button button_removemed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_med);

        String medID = getIntent().getStringExtra("MED_ID");
        String medName = getIntent().getStringExtra("MED_NAME");
        DocumentReference medRef = db.collection("medications").document(medID);

        tvMedName = (TextView) findViewById(R.id.chosen_med_name);
        tvMedName.setText(medName);

        tvMedPrescribed = (TextView) findViewById(R.id.chosen_med_prescribed);
        tvMedTaken = (TextView) findViewById(R.id.chosen_med_taken);
        tvScheduledTime = (TextView) findViewById(R.id.chosen_med_scheduledtime);
        tvExpirationDate = (TextView) findViewById(R.id.chosen_med_expiration);

        button_prescribe = (Button) findViewById(R.id.button_prescribe);
        button_takemed = (Button) findViewById(R.id.button_take_med);
        button_renewmed = (Button) findViewById(R.id.button_renew_med);
        button_removemed = (Button) findViewById(R.id.button_remove);

        final String[] prescribed = new String[1];
        final String[] taken = new String[1];
        final String[] scheduledTime = new String[1];
        final String[] expirationDate = new String[1];

        medRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            prescribed[0] = documentSnapshot.getString("_prescribed");
                            taken[0] = documentSnapshot.getString("hasTaken");
                            scheduledTime[0] = documentSnapshot.getString("scheduledTime");
                            expirationDate[0] = documentSnapshot.getString("expirationDate");

                            tvMedPrescribed.setText("Prescribed: "+ prescribed[0]);
                            tvMedTaken.setText("Taken Today: "+ taken[0]);
                            tvScheduledTime.setText("Scheduled Time: "+ scheduledTime[0]);
                            tvExpirationDate.setText("Expiration Date: "+ expirationDate[0]);

                            if(prescribed[0].equals("yes"))
                            {
                                button_prescribe.setVisibility(View.INVISIBLE);
                                button_renewmed.setVisibility(View.VISIBLE);
                                button_removemed.setVisibility(View.VISIBLE);
                                button_takemed.setVisibility(View.VISIBLE);

                                tvMedTaken.setText("Taken: "+ taken[0]);
                                tvScheduledTime.setText("Scheduled Time: "+ fixTime(scheduledTime[0]));
                                tvExpirationDate.setText("Expiration Date: "+ expirationDate[0]);
                            }
                            else
                            {
                                button_prescribe.setVisibility(View.VISIBLE);

                                tvMedTaken.setText("");
                                tvScheduledTime.setText("");
                                tvExpirationDate.setText("");
                            }
                        }
                        else
                        {
                            Toast.makeText( ChosenMedActivity.this, "Document does not exist!", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( ChosenMedActivity.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
                    }
                });

        button_prescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medRef.update("_prescribed", "yes").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChosenMedActivity.this, "Successfully updated.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChosenMedActivity.this, "Updating failed!", Toast.LENGTH_SHORT).show();
                    }
                });

                RefreshActivity();
            }
        });

        button_renewmed.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String newdate = (LocalDate.parse(expirationDate[0])).plusMonths(6).toString(); // add 6 months to expiration date

                medRef.update("expirationDate", newdate);
                RefreshActivity();
            }
        });

        button_takemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medRef.update("hasTaken", "yes");
                RefreshActivity();
            }
        });

        button_removemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medRef.update("_prescribed", "no");
                RefreshActivity();
            }
        });

    }

    // Make the time string easy to read
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String fixTime(String time)
    {
        LocalTime milTime = LocalTime.parse(time);
        String regTime;

        // show PM instead of AM for noon - 12:59 PM
        if(milTime.isAfter(LocalTime.parse("11:59:59")) && milTime.isBefore(LocalTime.parse("13:00:00")))
        {
            return milTime.toString()+" PM";
        }

        // show 12:00 - 12:59 AM instead of 00:00 AM
        if(milTime.isAfter(LocalTime.parse("00:00:00")) && milTime.isBefore(LocalTime.parse("01:00:00")))
        {
            return milTime.plusHours(12).toString()+" AM";
        }

        // for most cases
        if(milTime.isAfter(LocalTime.parse("12:59:59"))) // noon or after (PM)
        {
            regTime = milTime.minusHours(12).toString()+" PM";
        }
        else // 11:59 or before (AM)
        {
            regTime = milTime.toString()+" AM";
        }

        return regTime;
    }

    public void RefreshActivity()
    {
        finish();
        startActivity(getIntent());
    }
}
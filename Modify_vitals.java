package com.example.login1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Modify_vitals extends AppCompatActivity {
    EditText pr, cl, bp, gl, bt;

    PopupWindow popUp;
    boolean click = true;
    Button save;


    DatabaseReference dbr;
    FirebaseAuth off = FirebaseAuth.getInstance();
    String userEmail = off.getCurrentUser().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_vitals);

        pr = findViewById(R.id.pl);
        cl = findViewById(R.id.cl);
        bp = findViewById(R.id.bp);
        gl = findViewById(R.id.gl);
        bt = findViewById(R.id.bt);

        save = findViewById(R.id.save_mod);

        dbr = FirebaseDatabase.getInstance().getReference().child("User Info");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pulse = pr.getText().toString();
                String chole = cl.getText().toString();
                String blood = bp.getText().toString();
                String gluco = gl.getText().toString();
                String bodyt = bt.getText().toString();

                int pulseInt = -1, choleInt = -1, bloodInt = -1, glucoInt = -1, bodytInt = -1;

                if(!pulse.isEmpty())
                    pulseInt = Integer.parseInt(pulse);
                if(!chole.isEmpty())
                    choleInt = Integer.parseInt(chole);
                if(!blood.isEmpty())
                    bloodInt = Integer.parseInt(blood);
                if(!gluco.isEmpty())
                    glucoInt = Integer.parseInt(gluco);
                if(!bodyt.isEmpty())
                    bodytInt = Integer.parseInt(bodyt);

                if(TextUtils.isEmpty(pulse))
                {
                    Toast.makeText(Modify_vitals.this, "Pulse Rate field is empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(chole))
                {
                    Toast.makeText(Modify_vitals.this, "Cholestrol field is empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(blood))
                {
                    Toast.makeText(Modify_vitals.this, "Blood Pressure field is empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(gluco))
                {
                    Toast.makeText(Modify_vitals.this, "Glucose Field is empty", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(bodyt))
                {
                    Toast.makeText(Modify_vitals.this, "Body Temperature field is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String warningMsg = "";

                    if((pulseInt > 110 || pulseInt < 50) && pulseInt != -1)
                    {

                        warningMsg += "\nPulse rate is abnormal.";
                    }

                    if((choleInt > 135 || choleInt < 90) && choleInt != -1)
                    {
                        warningMsg += "\nCholesterol levels are abnormal.";
                    }

                    if((bloodInt > 140 || bloodInt < 0) && bloodInt != -1)
                    {
                        warningMsg += "\nBlood pressure is abnormal.";
                    }

                    if((glucoInt > 150 || glucoInt < 70) && glucoInt != -1)
                    {
                        warningMsg += "\nGlucose levels are abnormal.";
                    }

                    if((bodytInt > 105 || bodytInt < 95) && bodytInt != -1)
                    {
                        warningMsg += "\nBody temperature is abnormal.";
                    }

                    if(!warningMsg.equals(""))
                    {
                        Toast.makeText(Modify_vitals.this, warningMsg+"\nPlease see your physician immediately!", Toast.LENGTH_LONG).show();
                    }

                    Intent tomain = new Intent(Modify_vitals.this, Vitals_main.class);
                    startActivity(tomain);
                    sendData();
                }
            }
        });


    }

    public void sendData()
    {




        String pulse = pr.getText().toString();
        String chole = cl.getText().toString();
        String blood = bp.getText().toString();
        String gluco = gl.getText().toString();
        String bodyt = bt.getText().toString();

        Vitals user = new Vitals(gluco,pulse, chole, bodyt, blood);

        userEmail= userEmail.replace(".", ",");

        dbr.child("Vitals").child(userEmail).setValue(user);

        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();














    }
}
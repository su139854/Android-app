package com.example.login1;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Medication implements Serializable
{
    public String id;
    public String name;
    public String scheduledTime;
    public String expirationDate;
    public boolean hasTaken = false;
    public boolean isExpired = false;
    public List<String> conflictingMeds;

    // constructors
    public Medication()
    {

    };

    public Medication(String _id, String _name, String _conflicts)
    {
        id = _id;
        name = _name;
        conflictingMeds = new ArrayList<String>();
        String[] temp = _conflicts.split(",");

        conflictingMeds.addAll(Arrays.asList(temp));
    };

    // getters
    public String GetId() { return id; }
    public String GetName(){
        return name;
    }

    // methods
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean CheckExpired()
    {
        if(LocalDate.now().isAfter(LocalDate.parse(expirationDate)))
            isExpired = true;
        else
            isExpired = false;

        return isExpired;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void RequestRenewal()
    {
        expirationDate = (LocalDate.parse(expirationDate)).plusMonths(3).toString();
    }

}


package com.example.login1;

public class Vitals
{
    String Glucose, Pulse, Cholestrol, Body, Blood;

    public Vitals()
    {

    }

    public Vitals(String glucose, String pulse, String cholestrol, String body, String blood) {
        Glucose = glucose;
        Pulse = pulse;
        Cholestrol = cholestrol;
        Body = body;
        Blood = blood;
    }

    public String getGlucose() {
        return Glucose;
    }

    public String getPulse() {
        return Pulse;
    }

    public String getCholestrol() {
        return Cholestrol;
    }

    public String getBody() {
        return Body;
    }

    public String getBlood() {
        return Blood;
    }
}

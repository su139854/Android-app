package com.example.login1;

public class Data {

    String email;
    String DName;
    Double weight;
    int age;
    String birth;
    String gender;
    String height;

    public void Data(){}


    public Data(String email, String DName, Double weight, int age, String birth, String gender, String height)
    {
        this.email = email;
        this.DName = DName;
        this.weight = weight;
        this.age = age;
        this.birth = birth;
        this.gender = gender;
        this.height = height;
    }

    public Data(String DName, Double weight, int age, String birth, String gender, String height)
    {
        this.DName = DName;
        this.weight = weight;
        this.age = age;
        this.birth = birth;
        this.gender = gender;
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public Data(String DName)
    {
        this.DName = DName;
    }

    public String getBirth() {
        return birth;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getDName()
    {
        return DName;
    }

    public Double getWeight()
    {
        return weight;
    }

    public int getAge()
    {
        return age;
    }
}

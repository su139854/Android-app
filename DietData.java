package com.example.login1;

public class DietData {

    String foodName;
    String calCount;
    String weight;

    public void DietData(){}


    public DietData(String _foodName, String _calCount, String _weight)
    {
        foodName = _foodName;
        calCount = _calCount;
        weight = _weight;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCalCount() {
        return calCount;
    }

    public String getWeight() {
        return weight;
    }
}

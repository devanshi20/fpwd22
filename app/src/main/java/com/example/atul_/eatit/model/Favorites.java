package com.example.atul_.eatit.model;

/**
 * Created by Admin on 16-Mar-18.
 */

public class Favorites {

    private String FoodId;

    public Favorites()
    {

    }

    public Favorites(String foodId)
    {
        FoodId=foodId;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }


}

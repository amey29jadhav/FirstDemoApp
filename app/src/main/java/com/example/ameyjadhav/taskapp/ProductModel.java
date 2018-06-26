package com.example.ameyjadhav.taskapp;

import org.json.JSONObject;

public class ProductModel {

    public String id;

    public String title;

    public String desc;

    public int price;

    public String imageurl;

    public int quantity;

    public ProductModel(JSONObject jsonObject){
        id = jsonObject.optString("id");
        title = jsonObject.optString("title");
        desc = jsonObject.optString("desc");
        price = jsonObject.optInt("price");
        quantity = jsonObject.optInt("quantity");
        imageurl = jsonObject.optString("imgurl");
    }

}

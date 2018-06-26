package com.example.ameyjadhav.taskapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ConstantsData {

    public static String ProductArrayJson = "[{\"id\":\"cv1\",\"title\":\"t is designed with beautiful Zari Embroidery Lace Border work in form of traditional motiff. This Coloured Chiffon Saree Material For Indian Women From our company Is Designed As Per The L atest Trends To Keep You In Sync With High Fashion And With Your Wedding Occasion with unstiched Matching Blouse. Adorn yourself with this perfectly bollywood inspired creation.\",\"desc\":\"Good quality\",\"price\":1223,\"imgurl\":\"https://lh3.googleusercontent.com/vu0CaEDc_lzAonklV5i_BgGOHcRKDdla5utqtsSoCN8FdoEsEMFM8cYn7HOaJcPyLDc\",\"quantity\":1},{\"id\":\"cv2\",\"title\":\"Classic Silk saree in Blue with Butta Work and Traditional contrast Zari Pallu in ellaborate design and Unstiched blouse. Make this classic Silk Blend a part of your wardrobe \",\"desc\":\"Good quality\",\"price\":1224,\"imgurl\":\"https://img3.craftsvilla.com/image/upload/w_200,h_300,c_lfill,f_auto/C/V/CV-36036-MCRAF23112130030-1527152264-Craftsvilla_1.jpg\",\"quantity\":1},{\"id\":\"cv3\",\"title\":\"Classic Silk saree in Maroon with Golden Zari Border with Traditional Golden Zari Pallu in ellaborate design and Unstiched blouse. Make this classic Silk Blend a part of your wardrobe\",\"desc\":\"Good quality\",\"price\":1225,\"imgurl\":\"https://img3.craftsvilla.com/image/upload/w_200,h_300,c_lfill,f_auto/C/V/CV-36036-MCRAF85429694950-1527152263-Craftsvilla_1.jpg\",\"quantity\":1},{\"id\":\"cv4\",\"title\":\"Classic Silk saree in Pink with Paisley Border and Traditional contrast Zari Pallu in elaborate design and Unstitched blouse. Make this classic Silk Blend a part of your wardrobe  \",\"desc\":\"Good quality\",\"price\":1225,\"imgurl\":\"https://img3.craftsvilla.com/image/upload/w_200,h_300,c_lfill,f_auto/C/V/CV-36036-MCRAF87396803780-1527152261-Craftsvilla_1.jpg\",\"quantity\":1},{\"id\":\"cv5\",\"title\":\"Classic Silk saree in Pink with Paisley Border and Traditional contrast Zari Pallu in elaborate design and Unstitched blouse. Make this classic Silk Blend a part of your wardrobe  \",\"desc\":\"Good quality\",\"price\":1226,\"imgurl\":\"https://img3.craftsvilla.com/image/upload/w_200,h_300,c_lfill,f_auto/C/V/CV-36036-MCRAF32372143200-1527152262-Craftsvilla_1.jpg\",\"quantity\":1},{\"id\":\"cv6\",\"title\":\"Classic Silk saree in Pink with Paisley Border and Traditional contrast Zari Pallu in elaborate design and Unstitched blouse. Make this classic Silk Blend a part of your wardrobe  \",\"desc\":\"Good quality\",\"price\":1227,\"imgurl\":\"https://img3.craftsvilla.com/image/upload/w_200,h_300,c_lfill,f_auto/C/V/CV-36036-MCRAF47993430570-1527152224-Craftsvilla_1.jpg\",\"quantity\":1}]";
    public static final String INCREMENTBROADCAST = "increment";
    public static final String DECREMENTBROADCAST = "decrement";
    public static final String REMOVEBROADCAST = "remove";
    public static int CartSubTotal;


    public static ArrayList<ProductModel> getProductList() {
        ArrayList<ProductModel> productModels = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(ProductArrayJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                ProductModel productModel = new ProductModel(jsonArray.optJSONObject(i));
                productModels.add(productModel);

            }
        } catch (Exception e) {
            System.out.printf("");
        }
        return productModels;

    }

    public static ProductModel getProductModel(String id) {
        ProductModel productModel = null;
        try {
            JSONArray jsonArray = new JSONArray(ProductArrayJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                productModel = new ProductModel(jsonArray.optJSONObject(i));
                if (id.equals(productModel.id)) {
                    return productModel;
                }

            }
        } catch (Exception e) {
        }
        return productModel;
    }



    public static ArrayList<ProductModel> getAllCartProducts(Context context){
        ArrayList<ProductModel> productModels = new ArrayList<>();
        try {
            String ProductIds= getCartProducts(context);
            List<String> myList = new ArrayList<String>(Arrays.asList(ProductIds.split(",")));

            for (int i = 0; i < myList.size(); i++) {
                try {
                    JSONArray jsonArray = new JSONArray(ProductArrayJson);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        if(myList.get(i).equalsIgnoreCase(jsonArray.optJSONObject(j).optString("id"))) {
                            ProductModel productModel = new ProductModel(jsonArray.optJSONObject(j));
                            productModels.add(productModel);
                            CartSubTotal += productModel.price;
                        }

                    }
                } catch (Exception e) {
                    System.out.printf("");
                }

            }
        } catch (Exception e) {
            System.out.printf("");
        }
        return productModels;


    }

    public static void setQuantity(Context context, int quantity) {
        SharedPreferences prefs = context.getSharedPreferences("CRAFTSVILLA_PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("quantity", quantity);
        editor.apply();
    }

    public static int getQuantity(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("CRAFTSVILLA_PREFERENCE", MODE_PRIVATE);
        return prefs.getInt("quantity", 1);
    }

    public static void setCartProducts(Context context, String id) {
        SharedPreferences prefs = context.getSharedPreferences("CRAFTSVILLA_PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cartproducts", id);
        editor.apply();
    }

    public static String getCartProducts(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("CRAFTSVILLA_PREFERENCE", MODE_PRIVATE);
        return prefs.getString("cartproducts", "");
    }
}

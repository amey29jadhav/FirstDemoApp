package com.example.ameyjadhav.taskapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {
    TextView crosstextview, sharetextview, discountedpricetextview, actualpricetextview, desctextview;
    Typeface fontAwesomeFont;
    String id;
    ProductModel productModel;
    ImageView imageView;
    Context mcontext;
    Button cartbutton, buybutton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        mcontext = this;
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=  null){
            actionBar.hide();
        }

        if(getIntent() != null && getIntent().getExtras() != null){
            id = getIntent().getExtras().getString("id");
            productModel = ConstantsData.getProductModel(id);
        }
        init(productModel);
    }

    private void init(final ProductModel productModel) {
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "FontAwesome.otf");

        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(mcontext)
                .load(productModel.imageurl)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,ImageZoomActivity.class);
                intent.putExtra("imgurl",productModel.imageurl);
                mcontext.startActivity(intent);
            }
        });

        crosstextview = (TextView) findViewById(R.id.crosstextview);
        crosstextview.setTypeface(fontAwesomeFont);

        sharetextview = (TextView) findViewById(R.id.sharetextview);
        sharetextview.setTypeface(fontAwesomeFont);

        discountedpricetextview = (TextView) findViewById(R.id.discountedpricetextview);
        discountedpricetextview.setText(getResources().getString(R.string.inr)+Integer.toString( productModel.price));

        actualpricetextview = (TextView) findViewById(R.id.actualpricetextview);
        strikeThroughText(actualpricetextview);

        cartbutton = (Button) findViewById(R.id.cartbutton);
        cartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cartProductsIds = ConstantsData.getCartProducts(mcontext);

                if(!TextUtils.isEmpty(cartProductsIds)) {
                    if(!cartProductsIds.contains(productModel.id)) {
                        String concatjson = cartProductsIds + ',' + productModel.id;
                        ConstantsData.setCartProducts(mcontext, concatjson);
                    }

                }else{

                    ConstantsData.setCartProducts(mcontext,productModel.id);

                }


                Intent intent = new Intent(mcontext, MyCartActivity.class);
                intent.putExtra("id",productModel.id);
                mcontext.startActivity(intent);
            }
        });

        desctextview = (TextView) findViewById(R.id.desctextview);
        desctextview.setText(productModel.title);
    }

    private void strikeThroughText(TextView price){
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}

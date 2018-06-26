package com.example.ameyjadhav.taskapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    String images[] = {"https://img3.craftsvilla.com/image/upload/f_auto/assets1/banner-craftsvilla/cvfeeds/1529572970_anarkalis_hero.jpg",
    "https://img3.craftsvilla.com/image/upload/f_auto/assets1/banner-craftsvilla/cvfeeds/1529573585_sarees_hero.jpg",
    "https://img3.craftsvilla.com/image/upload/f_auto/assets1/banner-craftsvilla/cvfeeds/1529573573_lehengas_hero.jpg",
    "https://img3.craftsvilla.com/image/upload/f_auto/assets1/banner-craftsvilla/cvfeeds/1529572983_jewellery_hero.jpg",
    "https://img3.craftsvilla.com/image/upload/f_auto/assets1/banner-craftsvilla/cvfeeds/1529573596_suits_hero.jpg",
    "https://img3.craftsvilla.com/image/upload/f_auto/assets1/banner-craftsvilla/cvfeeds/1529572970_anarkalis_hero.jpg"};

    CustomPagerAdapter customPagerAdapter;
    //PageIndicator mIndicator;
    HorizontalScrollView horizontalScrollView;
    private LayoutInflater mInflater;
    private LinearLayout mGallery;
    LinearLayout gallerycontainer, indicatorcontainer;
    Context mcontext;
    public ArrayList<ProductModel> lstModel;
    Typeface fontAwesomeFont;
    TextView arrowtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "FontAwesome.otf");

        mcontext = this;
        mInflater = LayoutInflater.from(this);
        lstModel = ConstantsData.getProductList();
        initView();
        initDots();
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        if(images != null) {
            customPagerAdapter = new CustomPagerAdapter(MainActivity.this, images);
            viewPager.setAdapter(customPagerAdapter);
        }

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (indicatorcontainer != null) {
                    for (int i = 0; i < indicatorcontainer.getChildCount(); i++) {
                        if (position == i) {
                            TextView selectedview = (TextView)indicatorcontainer.getChildAt(i);
                            selectedview.setTextColor(Color.RED);
                        }else{
                            TextView selectedview = (TextView)indicatorcontainer.getChildAt(i);
                            selectedview.setTextColor(Color.WHITE);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        arrowtextview = (TextView) findViewById(R.id.arrowtextview);
        arrowtextview.setTypeface(fontAwesomeFont);
    }

    private void initDots() {
        indicatorcontainer = (LinearLayout) findViewById(R.id.indicatorcontainer);

        for (int i = 0; i < images.length; i++) {

            TextView textView = new TextView(mcontext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(fontAwesomeFont);
            textView.setPadding(5,5,5,5);
            textView.setTextSize(8.0f);
            textView.setText(getResources().getString(R.string.dots));
            indicatorcontainer.addView(textView);
        }
    }


    private void initView() {
        mGallery = (LinearLayout) findViewById(R.id.gallerycontainer);

        for (int i = 0; i < lstModel.size(); i++) {

            View view = mInflater.inflate(R.layout.activity_gallery_item,
                    mGallery, false);
            final ImageView img = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);

            img.setTag(lstModel.get(i).id);

            Picasso.with(mcontext)
                    .load(lstModel.get(i).imageurl)
                    .into(img);
            mGallery.addView(view);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext,ProductDetailActivity.class);
                    intent.putExtra("id",img.getTag().toString());
                    mcontext.startActivity(intent);
                }
            });
        }
    }


}

package com.example.ameyjadhav.taskapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter mAdapter;
    ArrayList<ProductModel> productModel = new ArrayList<>();
    private String id;
    Typeface fontAwesomeFont;
    Context mcontext;
    TextView shippingtextview, paytextview, subtotaltextview, backarrow;
    int shippingcharge = 99;

    ProductModel product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        mcontext = this;

        productModel = ConstantsData.getAllCartProducts(mcontext);

        if (getIntent() != null && getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString("id");
            init(id);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void init(String id) {

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "FontAwesome.otf");
        product = ConstantsData.getProductModel(id);
        //productModel.add(product);
        mAdapter = new CartAdapter(mcontext, productModel, fontAwesomeFont);
        recyclerView = (RecyclerView) findViewById(R.id.productlistview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration itemDecor = new DividerItemDecoration(mcontext, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(mAdapter);

        subtotaltextview = (TextView) findViewById(R.id.subtotaltextview);
        subtotaltextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(product.price));


        shippingtextview = (TextView) findViewById(R.id.shippingtextview);
        shippingtextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(99));

        paytextview = (TextView) findViewById(R.id.paytextview);
        int payableamount = shippingcharge + product.price;
        paytextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(payableamount));

        backarrow = (TextView) findViewById(R.id.backarrow);
        backarrow.setTypeface(fontAwesomeFont);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

        private List<ProductModel> productList;
        Typeface fontAwesomeFont;
        Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, price, incrementtextview, decrementtextview, closetextview, numbertextview;
            ImageView productimageview;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.productdesc);
                price = (TextView) view.findViewById(R.id.pricetextview);
                incrementtextview = (TextView) view.findViewById(R.id.incrementtextview);
                incrementtextview.setTypeface(fontAwesomeFont);
                decrementtextview = (TextView) view.findViewById(R.id.decrementtextview);
                decrementtextview.setTypeface(fontAwesomeFont);
                closetextview = (TextView) view.findViewById(R.id.closetextview);
                closetextview.setTypeface(fontAwesomeFont);
                productimageview = (ImageView) view.findViewById(R.id.productimageview);
                numbertextview = (TextView) view.findViewById(R.id.numbertextview);
            }
        }


        public CartAdapter(Context context, List<ProductModel> productList, Typeface fontAwesomeFont) {
            this.context = context;
            this.productList = productList;
            this.fontAwesomeFont = fontAwesomeFont;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_cart_items, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ProductModel productModel = productList.get(position);
            holder.title.setText(productModel.title);
            holder.price.setText(getResources().getString(R.string.inr) + " " + Integer.toString(productModel.price));

            Picasso.with(context)
                    .load(productModel.imageurl)
                    .into(holder.productimageview);

            holder.incrementtextview.setTag(productModel);
            holder.incrementtextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProductModel model = (ProductModel)holder.incrementtextview.getTag();
                    int quantity = model.quantity;

                    if (quantity == 1)
                        return;

                    quantity++;
                    model.quantity = quantity;

                    holder.numbertextview.setText(Integer.toString(quantity));

                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(ConstantsData.ProductArrayJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            if(jsonArray.optJSONObject(i).optString("id").equalsIgnoreCase(model.id)) {
                                jsonArray.optJSONObject(i).put("quantity", quantity);
                                ConstantsData.CartSubTotal += jsonArray.optJSONObject(i).getInt("price");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    ConstantsData.ProductArrayJson = jsonArray.toString();

                    /*int quantitycount = ConstantsData.getQuantity(context);
                    quantitycount++;
                    ConstantsData.setQuantity(mcontext, quantitycount);
*/
                    Intent intent = new Intent(ConstantsData.INCREMENTBROADCAST);
                    mcontext.sendBroadcast(intent);
                }
            });

            holder.decrementtextview.setTag(productModel);
            holder.decrementtextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProductModel model = (ProductModel)holder.decrementtextview.getTag();
                    int quantity = model.quantity;

                    if (quantity == 1)
                            return;

                    quantity--;
                    model.quantity = quantity;

                    holder.numbertextview.setText(Integer.toString(quantity));

                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(ConstantsData.ProductArrayJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            if(jsonArray.optJSONObject(i).optString("id").equalsIgnoreCase(model.id)) {
                                jsonArray.optJSONObject(i).put("quantity", quantity);
                                ConstantsData.CartSubTotal -= jsonArray.optJSONObject(i).getInt("price");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    ConstantsData.ProductArrayJson = jsonArray.toString();
                  /*  int quantitycount = ConstantsData.getQuantity(context);
                    if (quantitycount == 1)
                        return;
                    else {
                        quantitycount--;
                        ConstantsData.setQuantity(mcontext, quantitycount);
                    }*/

                    Intent intent = new Intent(ConstantsData.DECREMENTBROADCAST);
                    mcontext.sendBroadcast(intent);

                }
            });

            holder.closetextview.setTag(productModel.id);
            holder.closetextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConstantsData.REMOVEBROADCAST);
                    intent.putExtra("id", holder.closetextview.getTag().toString());
                    mcontext.sendBroadcast(intent);
                }
            });
            holder.numbertextview.setText(Integer.toString(productModel.quantity));
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }
    }

    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter(ConstantsData.INCREMENTBROADCAST);
        registerReceiver(increment, intentFilter);

        IntentFilter intentFilter1 = new IntentFilter(ConstantsData.DECREMENTBROADCAST);
        registerReceiver(decrement, intentFilter1);

        IntentFilter intentFilter2 = new IntentFilter(ConstantsData.REMOVEBROADCAST);
        registerReceiver(remove, intentFilter2);
    }

    public void unregisterBroadcast() {
        if (increment != null)
            unregisterReceiver(increment);

        if (decrement != null)
            unregisterReceiver(decrement);


        if (remove != null)
            unregisterReceiver(remove);


    }

    private BroadcastReceiver increment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int subtotal = (ConstantsData.getQuantity(mcontext) * product.price);
            //subtotaltextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(subtotal));
            subtotaltextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(ConstantsData.CartSubTotal));
            int totalpayable = shippingcharge + ConstantsData.CartSubTotal;
            paytextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(totalpayable));
            mAdapter.notifyDataSetChanged();
        }
    };

    private BroadcastReceiver decrement = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int subtotal = (ConstantsData.getQuantity(mcontext) * product.price);
            //subtotaltextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(subtotal));
            subtotaltextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(ConstantsData.CartSubTotal));
            int totalpayable = shippingcharge + ConstantsData.CartSubTotal;
            paytextview.setText(getResources().getString(R.string.inr) + " " + Integer.toString(totalpayable));
            mAdapter.notifyDataSetChanged();
        }
    };

    private BroadcastReceiver remove = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String ProductIds = ConstantsData.getCartProducts(mcontext);
            List<String> myList = new ArrayList<String>(Arrays.asList(ProductIds.split(",")));
            if (intent != null && intent.getExtras() != null) {
                String id = intent.getExtras().getString("id");
                myList.remove(id);
            }
            StringBuilder csvBuilder = new StringBuilder();

            if (myList.size() > 0) {
                for (String city : myList) {
                    csvBuilder.append(city);
                    csvBuilder.append(",");
                }
                String csv = csvBuilder.toString();

                //Remove last comma
                csv = csv.substring(0, csv.length() - ",".length());
                ConstantsData.setCartProducts(mcontext, csv);

            }else{
                ConstantsData.setCartProducts(mcontext, "");

            }
            mAdapter.productList = ConstantsData.getAllCartProducts(mcontext);
            mAdapter.notifyDataSetChanged();
        }

    };


    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

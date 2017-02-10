package com.cebs.pingfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemActivity extends AppCompatActivity {

    public static int MENUID;
    public static String MENUNAME;
    FloatingActionButton button;
    TextView title;
    ListView listItem;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ArrayList<FoodItem> items=new ArrayList<FoodItem>();
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        listItem=(ListView)findViewById(R.id.listItem);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(MENUNAME);
        button=(FloatingActionButton)findViewById(R.id.cart);
        title=(TextView)findViewById(R.id.carttitle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Cart.getSize()>0) {
                    Intent intent = new Intent(ItemActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"No Items in Cart",Toast.LENGTH_LONG).show();
                }
            }
        });
        showCart();

        new Item().execute();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
    public void showCart()
    {
        if(Cart.getSize()>0)
        {
            title.setText(Cart.getSize()+"");
            title.setVisibility(View.VISIBLE);
            button.setVisibility(FloatingActionButton.VISIBLE);
        }
        else
        {
            title.setVisibility(View.GONE);
            button.setVisibility(FloatingActionButton.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();

    }

    public class Item extends AsyncTask<Void,Void,Void>
    {
        public  Item()
        {

        }
        @Override
        protected Void doInBackground(Void... params)
        {
            arraylist = new ArrayList<HashMap<String, String>>();

            try {
                JSONObject jsonObject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/items.jsp?id="+MENUID);
                Log.d("Test1","Test1");
                JSONArray jsonArray = jsonObject.getJSONArray("Items");

                Log.d("JSON",jsonArray.toString());
                arrayList=new ArrayList<String>();

                for (int i=0;i<jsonArray.length();i++)
                {
                    arrayList.add(jsonArray.getJSONObject(i).getString("item_name"));
                    FoodItem fi=new FoodItem();
                    fi.setId(Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
                    fi.setName(jsonArray.getJSONObject(i).getString("item_name"));
                    fi.setPrice(Double.parseDouble(jsonArray.getJSONObject(i).getString("item_price")));
                    fi.setLogo(jsonArray.getJSONObject(i).getString("logo"));
                    fi.setUnit(jsonArray.getJSONObject(i).getString("unit_name"));
                    items.add(fi);
                }


            }
            catch (Exception ex)
            {
                Toast.makeText(getBaseContext(),ex+"",Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            adapter=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
            listItem.setAdapter(adapter);
            listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    FoodItem foodItem= items.get(position);
                    ChooseItemActivity.ITEMID=foodItem.getId();
                    ChooseItemActivity.ITEMDESCRIPTION="Dummy";
                    ChooseItemActivity.ITEMNAME=foodItem.getName();
                    ChooseItemActivity.ITEMPRICE=foodItem.getPrice();
                    ChooseItemActivity.ITEMLOGO=foodItem.getLogo();
                    startActivity(new Intent(getBaseContext(),ChooseItemActivity.class));
                }
            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ItemActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Ping Food");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
    }
}

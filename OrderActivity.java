package com.cebs.pingfood;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity
{
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ArrayList<FoodItem> cartitems;
    OrderItemAdapter adapter;
    ArrayList<HashMap<String,String>> arraylist;
    ProgressDialog mProgressDialog;
    TextView txtTotal;
    PrepareOrder prepare;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        txtTotal=(TextView)findViewById(R.id.txtTotal);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Your Order");
        cartitems=Cart.getCart();
        prepare=new PrepareOrder();
        prepare.execute();

    }
    public void prepareOrder()
    {
        prepare.execute();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
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
    private class PrepareOrder extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(OrderActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Ping Food");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address


            try {
                // Locate the array name in JSON

                for (int i = 0; i < cartitems.size(); i++)
                {

                    FoodItem fi= cartitems.get(i);
                    HashMap<String, String> map = new HashMap<String,String>();


                    // Retrieve JSON Objects
                    map.put("id", fi.getId()+"");
                    map.put("item_name",fi.getName()+"" );
                    map.put("item_price", fi.getPrice()+"");
                    map.put("unit_name", fi.getUnit()+"");
                    map.put("item_qty", fi.getQty()+"");
                    map.put("item_total", fi.getTotal()+"");

                    // Set the JSON Objects into the array
                    arraylist.add(map);
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            mProgressDialog.dismiss();
            listview = (ListView) findViewById(R.id.orderList);
            // Pass the results into ListViewAdapter.java

            adapter = new OrderItemAdapter(OrderActivity.this, arraylist);
            // Set the adapter to the ListView
            adapter.notifyDataSetChanged();

            listview.setAdapter(adapter);
            txtTotal.setText("Total  : Rs. "+Cart.getTotal());
            // Close the progressdialog

        }
    }
}

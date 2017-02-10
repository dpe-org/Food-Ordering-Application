package com.cebs.pingfood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dhiraj.Pathak on 8/30/2016.
 */
public class OrderItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<FoodItem> cartItmes;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();
    TextView itemName, ItemPrice;
    Button editItem,placeOrder;
    ToggleButton btnEdit;
    LinearLayout layout;

    public OrderItemAdapter(Context context,
                            ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // Declare Variables
        TextView branch_name, branch_address1, branch_address2, branch_id;

        ImageView logo = null;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.orderadapter, parent, false);
        Log.d("ItemView",itemView.toString());
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        itemName = (TextView) itemView.findViewById(R.id.itemName);
        ItemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
        ImageButton btnRemove=(ImageButton) itemView.findViewById(R.id.btnRemove);
        placeOrder=(Button)itemView.findViewById(R.id.orderPlace);
        btnEdit=(ToggleButton)itemView.findViewById(R.id.btnEdit);
        layout=(LinearLayout)itemView.findViewById(R.id.linearlayout);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context,"Item Removed",Toast.LENGTH_LONG).show();
            }
        });
        Log.d("Data",resultp.toString());
        itemName.setText(resultp.get("item_name"));
        ItemPrice.setText(resultp.get("item_total"));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
       btnEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
           {
               Log.d("EDIT",isChecked+"");
               if(isChecked)
                   layout.setVisibility(View.VISIBLE);
               else
                   layout.setVisibility(View.GONE);
           }
       });
    return itemView;
    }
}
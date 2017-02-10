package com.cebs.pingfood;

import java.util.ArrayList;

/**
 * Created by dhiraj.pathak on 8/28/2016.
 */
public class Cart
{
    static ArrayList<FoodItem> myCart=new ArrayList<FoodItem>();
    public static void add(FoodItem fi)
    {
        myCart.add(fi);
    }
    public static int getSize()
    {
        return myCart.size();
    }
    public static void clear()
    {
        myCart.clear();
    }
    public static ArrayList<FoodItem> getCart()
    {
        return myCart;

    }
    public static void remove(int position)
    {
        myCart.remove(position);
    }
    public static double getTotal()
    {
        double total=0;
        for(int i=0;i<myCart.size();i++)
        {
            total+=((FoodItem)myCart.get(i)).getTotal();
        }
        return total;
    }
}

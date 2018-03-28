package com.mirka.app.ghmarket.DB;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Foster on 3/24/2018.
 */

@ParseClassName("Order")
public class Order extends ParseObject {


    private static final String KEY_PRODUCTS = "products";
    private static final String KEY_PROMOCODE = "code";
    private static final String KEY_PLACED = "placed";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_DISCOUNTED_PRICE = "discounted_price";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_DISPLAY_IMG = "dis_img";

    static DecimalFormat df = new DecimalFormat("#.00");

    public static ParseQuery<Order> getQuery() {
        return ParseQuery.getQuery(Order.class).setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
    }

    public static void getBag(final OnComplete complete) {
        getQuery().setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE)
                .whereEqualTo(KEY_OWNER, User.getCurrentUser())
                .whereEqualTo(KEY_PLACED, false)
                .getFirstInBackground(new GetCallback<Order>() {
                    @Override
                    public void done(Order object, ParseException e) {

                        if (object == null) {
                            // no bag found create a new bag
                            object = new Order();
                            object.setPlaced(false);

                        }

                        object.put(KEY_OWNER, User.getCurrentUser());
                        complete.complete(object, e);
                    }
                });
    }

    public static void getOrders(FindCallback<Order> callback) {
        getQuery()
                .whereEqualTo(KEY_OWNER, User.getCurrentUser())
                .whereEqualTo(KEY_PLACED, true)
                .findInBackground(callback);
    }

    public List<Purchase> getProducts() {
        JSONArray jsonArray = getJSONArray(KEY_PRODUCTS);
        List<Purchase> purchases = new ArrayList<>();

        if (jsonArray != null)
            for (int i = 0; i < jsonArray.length(); i++)
                try {
                    purchases.add(Purchase.fromJson(jsonArray.getString(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        return purchases;
    }

    public double getTotalPrice() {
        return getDouble(KEY_TOTAL_PRICE);
    }

    //total price
    public void setTotalPrice(double value) {
        String d = new DecimalFormat("#.00").format(value);
        put(KEY_TOTAL_PRICE, Double.parseDouble(d));
    }

    //discounted price
    public double getDiscountedPrice() {
        return getDouble(KEY_DISCOUNTED_PRICE);
    }

    public void setDiscountedPrice(double value) {
        String d = df.format(value);
        put(KEY_DISCOUNTED_PRICE, Double.parseDouble(d));
    }

    /**
     * adds a new prouct to your purchases
     *
     * @param new_purchase
     */
    public void addProduct(Purchase new_purchase) {
        JSONArray jsonArray = getJSONArray(KEY_PRODUCTS);

        if (jsonArray == null)
            jsonArray = new JSONArray();
        jsonArray.put(new_purchase.toJson());


        put(KEY_PRODUCTS, jsonArray);


    }

    /**
     * removes product from orders and update prices
     *
     * @param product
     */
    public void removeProduct(Product product) {
        JSONArray jsonArray = getJSONArray(KEY_PRODUCTS);
        if (jsonArray == null) return;
        JSONArray new_jsonArray = new JSONArray();

        //setting up new list
        for (int i = 0; i < jsonArray.length(); i++) {
            try {

                String id = Purchase.fromJson(jsonArray.getString(i)).getProduct_id();
                String _id = product.getObjectId();

                if (!Purchase.fromJson(jsonArray.getString(i)).getProduct_id().equals(product.getObjectId())) {
                    new_jsonArray.put(jsonArray.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        setTotalPrice(getTotalPrice() - product.getPrice());

        if (product.getDiscountedPrice() > 0)
            setDiscountedPrice(getDiscountedPrice() - product.getDiscountedPrice());
        else
            setDiscountedPrice(getDiscountedPrice() - product.getPrice());


        put(KEY_PRODUCTS, new_jsonArray);
    }

    public String getPromoCode() {
        return getString(KEY_PROMOCODE);
    }

    public void setPromoCode(String value) {
        put(KEY_PROMOCODE, value);
    }

    public boolean getPlaced() {
        return getBoolean(KEY_PLACED);
    }

    public void setPlaced(boolean placed) {
        put(KEY_PLACED, placed);
    }

    public String getDisplayImage() {
        return getString(KEY_DISPLAY_IMG);
    }

    public void setDisplayImage(String value) {
        put(KEY_DISPLAY_IMG, value);
    }

    public double getShipping() {
        return 0.2 * getTotalPrice();
    }


    /**
     * oncontinue interface
     */
    public interface OnComplete {
        void complete(Order order, Exception exception);
    }

}
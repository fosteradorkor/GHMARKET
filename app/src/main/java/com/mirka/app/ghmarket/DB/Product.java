package com.mirka.app.ghmarket.DB;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Foster on 3/20/2018.
 */
@ParseClassName("product")
public class Product extends ParseObject {
    private static final String KEY_IMAGES = "images";
    public static String KEY_PRODUCT_TYPE = "type";
    public static String KEY_PRODUCT_STYLE = "style";
    private static String KEY_TITLE = "title";
    private static String KEY_PRICE = "price";
    private static String KEY_DISCOUNTED_PRICE = "d_price";
    private static String KEY_DESCRIPTION = "description";
    private static String KEY_PRODUCT_SHIPPING = "shipping";
    private static String KEY_PRODUCT_MATERIAL = "material";
    private static String KEY_PRODUCT_SIZES = "sizes";
    private static String KEY_PRODUCT_COLORS = "colors";

    public static ParseQuery<Product> getQuery() {
        return ParseQuery.getQuery(Product.class).setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
    }

    //similar products
    public static ParseQuery<Product> getSimilarProducts(Product p) {
        return getQuery()
                .whereEqualTo(KEY_PRODUCT_TYPE, p.getProductType())
                .whereEqualTo(KEY_PRODUCT_STYLE, p.getProductStyle())
                .whereNotEqualTo(KEY_TITLE, p.getTitle());

    }

    //similar products
    public static ParseQuery<Product> getSimilarProducts(Product p, int limit) {
        return getSimilarProducts(p).setLimit(limit);

    }

    //getById
    public static void getById(final String id, GetCallback<Product> callback) {
        getQuery().fromLocalDatastore()
                .getInBackground(id)
                .continueWith(new Continuation<Product, Task<Product>>() {
                    @Override
                    public Task<Product> then(Task<Product> task) throws Exception {

                        if (task.getError() != null && task.getError() instanceof ParseException) {
                            //local error
                        } else {

                        }

                        //local datastore
                        return getQuery().getInBackground(id);
                    }
                })
                .continueWithTask(new Continuation<Task<Product>, Task<Product>>() {

                    @Override
                    public Task<Product> then(Task<Task<Product>> task) throws Exception {

                        //network
                        Product result = task.getResult().getResult();
                        return null;
                    }
                });
    }


    public static void getById(final String id, final OnComplete complete) {
        getQuery().getInBackground(id, new GetCallback<Product>() {
            @Override
            public void done(Product object, ParseException e) {
                complete.complete(object, e);
            }
        });

    }

    public static void getProducts(@Nullable String style, @Nullable String type, FindCallback<Product> callback) {
        ParseQuery<Product> query = getQuery();

        if (style != null) query.whereEqualTo(KEY_PRODUCT_STYLE, style);
        if (type != null) query.whereEqualTo(KEY_PRODUCT_TYPE, type);


        query.findInBackground(callback);

    }

    //    title
    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String value) {
        put(KEY_TITLE, value);
    }

    //    price
    public double getPrice() {
        return getDouble(KEY_PRICE);
    }

    public void setPrice(double value) {
        put(KEY_PRICE, value);
    }

    //    images
    public List<String> getImages() {
        return getList(KEY_IMAGES);
    }

    public void setImages(List<String> value) {
        put(KEY_IMAGES, value);
    }

    //    discounted price
    public double getDiscountedPrice() {
        String d = getString(KEY_DISCOUNTED_PRICE);
        if (d != null && !d.isEmpty())
            return Double.parseDouble(d);
        return getDouble(KEY_DISCOUNTED_PRICE);
    }

    public void setDiscountedPrice(double value) {
        put(KEY_IMAGES, value);
    }

    //    description
    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String value) {
        put(KEY_IMAGES, value);
    }

    //    product type
    public String getProductType() {
        return getString(KEY_PRODUCT_TYPE);
    }

    public void setProductType(String value) {
        put(KEY_IMAGES, value);
    }

    //    product style
    public String getProductStyle() {
        return getString(KEY_PRODUCT_STYLE);
    }

    public void setProductStyle(String value) {
        put(KEY_PRODUCT_STYLE, value);
    }

    //    product shipping
    public double getProductShipping() {
        return getDouble(KEY_PRODUCT_SHIPPING);
    }

    public void setProductShipping(Double value) {
        put(KEY_PRODUCT_SHIPPING, value);
    }

    //    product style
    public String getMaterial() {
        return getString(KEY_PRODUCT_MATERIAL);
    }

    public void setMaterial(String value) {
        put(KEY_PRODUCT_MATERIAL, value);
    }

    // product colors
    public List<String> getColors() {
        return getList(KEY_PRODUCT_COLORS);
    }

    public void setColors(List<String> value) {
        put(KEY_PRODUCT_COLORS, value);
    }

    // product sizes
    public List<String> getSizes() {
        return getList(KEY_PRODUCT_SIZES);
    }

    public void setSizes(List<String> value) {
        put(KEY_PRODUCT_SIZES, value);
    }

    public interface OnComplete {
        void complete(Product p, Exception e);
    }


}

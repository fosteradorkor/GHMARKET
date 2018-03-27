package com.mirka.app.ghmarket.DB;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Foster on 3/22/2018.
 */
@ParseClassName("category")
public class Category extends ParseObject {

    private static final String TAG = "categories";

    private static final String KEY_PRODUCTS = "products";
    private static final String KEY_TITLE = "title";
    public static OnComplete complete = new OnComplete() {
        @Override
        public void complete(List<Category> categories, Exception e) {

        }
    };
    private static Category c;

    public static ParseQuery<Category> getQuery() {
        return ParseQuery.getQuery(Category.class).setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
    }

    public static void getAll(final OnComplete complete) {

        getQuery().setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK).findInBackground(new FindCallback<Category>() {
            @Override
            public void done(List<Category> objects, ParseException e) {
                complete.complete(objects, e);
            }
        });
    }

    public static void getCategories(final OnComplete complete) {
        getQuery()
                .fromLocalDatastore()
                .findInBackground(new FindCallback<Category>() {
                    @Override
                    public void done(List<Category> objects, ParseException e) {
                        if (objects != null)
                            complete.complete(objects, e);

                        //get it online
                        getQuery()
                                .findInBackground(new FindCallback<Category>() {
                                    @Override
                                    public void done(List<Category> objects, ParseException e) {
                                        complete.complete(objects, e);
                                    }
                                });

                    }
                });

    }

    private List<ParseObject> _getProducts() {
        return getList(KEY_PRODUCTS);
    }

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public List<Product> getProducts() {
        return getList(KEY_PRODUCTS);
    }

    public interface OnComplete {
        void complete(List<Category> categories, Exception e);
    }
}

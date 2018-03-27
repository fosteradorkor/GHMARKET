package com.mirka.app.ghmarket.activities.product_detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mirka.app.ghmarket.DB.Product;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;

import java.util.List;

class ProductDetailViewModel extends ViewModel {
    MutableLiveData<Product> product = new MutableLiveData<>();
    MutableLiveData<List<Product>> similarProducts = new MutableLiveData<>();


    public LiveData<Product> getProduct(String productId) {

        Product.getById(productId, new Product.OnComplete() {
            @Override
            public void complete(Product p, Exception e) {
                if (p!=null) product.postValue(p);
            }
        });

        return product;
    }

    public LiveData<List<Product>> getSimilarProducts(final Product product) {

        Product.getSimilarProducts(product).findInBackground(new FindCallback<Product>() {
            @Override
            public void done(List<Product> objects, ParseException e) {
                if (e == null) similarProducts.setValue(objects);
            }
        });


        return similarProducts;
    }
}
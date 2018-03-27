package com.mirka.app.ghmarket.activities.store.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.product_detail.ProductDetailActivity;
import com.mirka.app.ghmarket.databinding.RecyclerItemProductBinding;
import com.mirka.app.ghmarket.misc.BindingViewHolder;

import java.util.List;


/**
 * Created by Foster on 3/24/2018.
 */

public class HomeGroupRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    public static final int MAX_RESULTS_PER_ROW = 5;
    private List<Product> products;
    private Context context;
    private boolean no_limit = false;


    public HomeGroupRecyclerAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public HomeGroupRecyclerAdapter(List<Product> products, Context context, boolean no_limit) {
        this.products = products;
        this.context = context;
        this.no_limit = true;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {

        bind(((RecyclerItemProductBinding) holder.getBinding()), products.get(position));
    }

    @Override
    public void onViewRecycled(BindingViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.recycler_item_product;
    }

    @Override
    public int getItemCount() {
        if (no_limit)
            return products.size();

        return products.size() > MAX_RESULTS_PER_ROW ? MAX_RESULTS_PER_ROW : products.size();
    }

    void bind(final RecyclerItemProductBinding holder, final Product product) {

        if (product.isDataAvailable()) {
            holder.setProduct(product);
            holder.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString(ProductDetailActivity.PRODUCT_ID, product.getObjectId());
                    Intent i = new Intent(context, ProductDetailActivity.class);
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });

        } else          //data unavailable => query
            Product.getById(product.getObjectId(), new Product.OnComplete() {
                @Override
                public void complete(final Product p, Exception e) {
                    if (p != null) {
                        holder.setProduct(p);
                        holder.getRoot().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle b = new Bundle();
                                b.putString(ProductDetailActivity.PRODUCT_ID, p.getObjectId());
                                Intent i = new Intent(context, ProductDetailActivity.class);
                                i.putExtras(b);
                                context.startActivity(i);
                            }
                        });
                    }
                }
            });


    }

}

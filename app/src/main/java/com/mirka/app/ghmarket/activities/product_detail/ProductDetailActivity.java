package com.mirka.app.ghmarket.activities.product_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirka.app.ghmarket.BR;
import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.DB.Purchase;
import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.store.adapters.HomeGroupRecyclerAdapter;
import com.mirka.app.ghmarket.databinding.ActivityProductActivityBinding;
import com.mirka.app.ghmarket.misc.BindingViewHolder;
import com.mirka.app.ghmarket.misc.Util;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "prod_id";

    ActivityProductActivityBinding layout;
    String product_id = null;

    ProductDetailViewModel model;

    //size and color
    int selected_size = -1;
    int selected_color = -1;


    boolean added = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = DataBindingUtil.setContentView(this, R.layout.activity_product_activity);

        product_id = getIntent().getExtras().getString(PRODUCT_ID, null);

        if (product_id == null) {
            finish();
        }

        model = ViewModelProviders.of(this).get(ProductDetailViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {

        //toolbar
        layout.toolbar.setNavigationIcon(R.drawable.ic_android_arrow_back);
        layout.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        model.getProduct(product_id).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable final Product product) {

//              product  databinding
                layout.setProduct(product);


                layout.carousel.setImageListener(new ImageListener() {
                    @Override
                    public void setImageForPosition(int position, ImageView imageView) {
                        Picasso.get().load(product.getImages().get(position)).into(imageView);
                    }
                });
                layout.carousel.setPageCount(product != null ? product.getImages().size() : 0);

                //variations
                if (product.getSizes().size() != 0)
                    layout.listSizes.setAdapter(new VariationRecyclerAdapter(product.getSizes(), R.layout.recycler_item_variation_size));
                if (product.getColors().size() != 0)
                    layout.listColors.setAdapter(new VariationRecyclerAdapter(product.getColors(), R.layout.recycler_item_variation_color));

                //recommended products
                model.getSimilarProducts(product).observe(ProductDetailActivity.this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> products) {
                        layout.rvRecommended.setAdapter(new HomeGroupRecyclerAdapter(products, ProductDetailActivity.this));
                    }
                });


                //add to cart
                layout.addToBag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Order.getBag(new Order.OnComplete() {
                            @Override
                            public void complete(Order order, Exception exception) {

                                Purchase new_purchase = new Purchase(product, null, null);

                                order.addProduct(new_purchase);
                                order.setTotalPrice(order.getTotalPrice() + product.getPrice());

                                //apply discount if there is a discount
                                if (product.getDiscountedPrice() > 0)
                                    order.setDiscountedPrice(order.getDiscountedPrice() + product.getDiscountedPrice());
                                else
                                    order.setDiscountedPrice(order.getDiscountedPrice() + product.getPrice());

                                order.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        //updating toolbar
                                        Util.setUpToolbar(layout.toolbar);

                                        Toast.makeText(ProductDetailActivity.this, R.string.added_to_bag, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });

                layout.isFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User currentUser = User.getCurrentUser();
                        if (isFavorite(product))
                            currentUser.removeFromFavorites(product);
                        else
                            currentUser.addToFavorites(product);

                        //updating records
                        currentUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    layout.isFavorite.setImageResource(isFavorite(product) ? R.drawable.ic_ios_heart : R.drawable.ic_ios_heart_outline);
                                }
                            }
                        });

                    }
                });

                //update toolbar
                Util.setUpToolbar(layout.toolbar);

                layout.isFavorite.setImageResource(isFavorite(product) ? R.drawable.ic_ios_heart : R.drawable.ic_ios_heart_outline);

            }
        });


    }

    /**
     * che
     *
     * @param product
     * @return
     */
    private boolean isFavorite(Product product) {
        List<Product> favorites = User.getCurrentUser().getFavorites();

        for (Product favorite : favorites) {
            if (favorite.getObjectId().equals(product.getObjectId())) {
                return true;
            }
        }
        return false;
    }


    class VariationRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {

        List<String> variations;
        int viewType;


        public VariationRecyclerAdapter(List<String> variations, int viewType) {
            this.variations = variations;
            this.viewType = viewType;
        }

        @Override
        public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingViewHolder(LayoutInflater.from(ProductDetailActivity.this).inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(BindingViewHolder holder, final int position) {


            holder.getBinding().getRoot().setBackgroundColor(Color.TRANSPARENT);
            if (viewType == R.layout.recycler_item_variation_color) {
                if (selected_color == position)
                    holder.getBinding().getRoot().setBackgroundResource(R.drawable.selected_variation_bg);
            } else {
                if (selected_size == position)
                    holder.getBinding().getRoot().setBackgroundResource(R.drawable.selected_variation_bg);
            }

            holder.getBinding().setVariable(BR.data, variations.get(position));
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viewType == R.layout.recycler_item_variation_color) {
                        int old = selected_color;
                        selected_color = position;
                        notifyItemChanged(old);
                        notifyItemChanged(selected_color);
                    } else {
                        int old = selected_size;
                        selected_size = position;
                        notifyItemChanged(old);
                        notifyItemChanged(selected_size);

                    }

                }
            });
        }


        @Override
        public int getItemViewType(int position) {
            return viewType;
        }

        @Override
        public int getItemCount() {
            return variations != null ? variations.size() : 0;
        }


    }


}

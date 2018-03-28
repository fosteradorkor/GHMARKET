package com.mirka.app.ghmarket.activities.purchases;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.DB.Purchase;
import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.ActivityOrderDetailBinding;
import com.mirka.app.ghmarket.databinding.RecyclerItemCheckoutBinding;
import com.mirka.app.ghmarket.misc.BindingViewHolder;
import com.parse.GetCallback;
import com.parse.ParseException;

import java.text.SimpleDateFormat;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String KEY_ORDER = "order_key";
    ActivityOrderDetailBinding layout;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyy");
    mRecyclerAdapter adapter;
    private Order order = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        String orderId = getIntent().getExtras().getString(KEY_ORDER, null);

        if (orderId == null) finish();

        layout.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Order.getQuery().getInBackground(orderId, new GetCallback<Order>() {
            @Override
            public void done(Order object, ParseException e) {
                if (e == null) {
                    OrderDetailActivity.this.order = object;
                    update();
                }

            }
        });


    }

    private void update() {
        adapter = new mRecyclerAdapter();
        String date = sdf.format(this.order.getCreatedAt());
        layout.toolbar.setTitle(date +"(On way)");

        layout.setUser(User.getCurrentUser());
        layout.setOrder(this.order);

        //setting recycler
        layout.recycler.setAdapter(adapter);
    }


    class mRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {

        @Override
        public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingViewHolder(LayoutInflater.from(OrderDetailActivity.this).inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(final BindingViewHolder holder, int position) {
            final Purchase purchase = order.getProducts().get(position);


            Product.getById(purchase.getProduct_id(), new Product.OnComplete() {
                @Override
                public void complete(Product p, Exception e) {

                    if (e == null)      //simple error check
                        if (p.getObjectId().equals(purchase.getProduct_id()))
                            bind(((RecyclerItemCheckoutBinding) holder.getBinding()), p);
                }
            });

        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.recycler_item_checkout;
        }

        @Override
        public int getItemCount() {
            return order.getProducts().size();
        }


        void bind(RecyclerItemCheckoutBinding holder, Product product) {
            holder.remove.setVisibility(View.GONE);
            holder.setProduct(product);

        }
    }
}

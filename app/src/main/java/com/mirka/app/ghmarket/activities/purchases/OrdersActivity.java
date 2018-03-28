package com.mirka.app.ghmarket.activities.purchases;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.ActivityOrdersBinding;
import com.mirka.app.ghmarket.databinding.RecyclerItemOrderBinding;
import com.mirka.app.ghmarket.misc.BindingViewHolder;
import com.mirka.app.ghmarket.misc.LinearSpacingItemDecoration;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    ActivityOrdersBinding layout;
    OrdersRecyclerAdapter adapter;
    List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = DataBindingUtil.setContentView(this, R.layout.activity_orders);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        adapter = new OrdersRecyclerAdapter();
        layout.recycler.setAdapter(adapter);
        layout.recycler.addItemDecoration(new LinearSpacingItemDecoration(8, LinearSpacingItemDecoration.VERTICAL, false));

        Order.getOrders(new FindCallback<Order>() {
            @Override
            public void done(List<Order> objects, ParseException e) {
                if (e == null) {
                    orders = objects;
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    class OrdersRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {

        @Override
        public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingViewHolder(LayoutInflater.from(OrdersActivity.this).inflate(viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(final BindingViewHolder holder, final int position) {

            final Order o = orders.get(position);

            if (!o.isDataAvailable())
                o.fetchInBackground(new GetCallback<Order>() {
                    @Override
                    public void done(Order object, ParseException e) {
                        // prevents overlaps
                        if (orders.get(position).hasSameId(object))
                            bind(((RecyclerItemOrderBinding) holder.getBinding()), object);
                    }
                });
            else
                //straight binding
                bind(((RecyclerItemOrderBinding) holder.getBinding()), o);
        }

        private void bind(RecyclerItemOrderBinding binding, Order order) {
            binding.setOrder(order);
        }


        @Override
        public int getItemViewType(int position) {
            return R.layout.recycler_item_order;
        }

        @Override
        public int getItemCount() {
            return orders == null ? 0 : orders.size();
        }
    }
}

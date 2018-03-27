package com.mirka.app.ghmarket.activities.checkout.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.DB.Purchase;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.FragmentBagBinding;
import com.mirka.app.ghmarket.databinding.RecyclerItemCheckoutBinding;
import com.mirka.app.ghmarket.misc.BindingViewHolder;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BagFragment extends Fragment {

    FragmentBagBinding layout;
    Order order;


    public BagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bag, container, false);
        layout = DataBindingUtil.bind(v);
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {

        layout.recycler.setNestedScrollingEnabled(false);
        Order.getBag(new Order.OnComplete() {

            @Override
            public void complete(Order order, Exception exception) {
                if (order != null) {
                    BagFragment.this.order = order;
                    layout.setOrder(order);
                    layout.recycler.setAdapter(new OrderRecyclerAdapter(order.getProducts()));


                }
            }
        });

        layout.btnApplyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setPromoCode(layout.promoCode.getText().toString());
                order.saveInBackground();
            }
        });

        layout.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, new PaymentInfoFragment()).commit();

            }
        });
    }


    class OrderRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {

        List<Purchase> purchases;
        private List<HashMap> products;

        public OrderRecyclerAdapter(List<Purchase> purchases) {
            this.purchases = purchases;
        }

//        public OrderRecyclerAdapter(List<Purchase> products) {
//            this.products = products;
//        }

        @Override
        public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingViewHolder(LayoutInflater.from(getContext()).inflate(viewType, parent, false));
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.recycler_item_checkout;
        }

        @Override
        public void onBindViewHolder(final BindingViewHolder holder, final int position) {
            Product.getById(purchases.get(position).getProduct_id(), new Product.OnComplete() {
                @Override
                public void complete(final Product p, Exception e) {
                    if (p != null) {
                        ((RecyclerItemCheckoutBinding) holder.getBinding()).setProduct(p);

                        //deleting item
                        ((RecyclerItemCheckoutBinding) holder.getBinding()).remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                remove
                                order.removeProduct(p);

//                                save changes
                                order.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            //save successful
                                            layout.setOrder(order);
                                            purchases.remove(position);
                                            notifyDataSetChanged();
                                        }


                                    }
                                });

                            }
                        });
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return purchases.size();
        }

    }
}

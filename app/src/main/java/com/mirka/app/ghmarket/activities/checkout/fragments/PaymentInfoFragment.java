package com.mirka.app.ghmarket.activities.checkout.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.FragmentPaymentInfoBinding;
import com.mirka.app.ghmarket.misc.CustomAlert;
import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentInfoFragment extends Fragment {

    FragmentPaymentInfoBinding layout;

    Order order;

    public PaymentInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_payment_info, container, false);
        layout = DataBindingUtil.bind(v);
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {

        Order.getBag(new Order.OnComplete() {
            @Override
            public void complete(Order order, Exception exception) {
                if (order != null) {
                    layout.setOrder(order);
                    PaymentInfoFragment.this.order = order;
                    layout.btnContinue.setText("PAY GH₵ " + order.getDiscountedPrice());

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

                if (validateInputs()) {

                    final CustomAlert alert = new CustomAlert(getContext());
                    alert.setType(CustomAlert.PROGRESS);
                    alert.show();

                    order.setPlaced(true);
                    order.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
//                            unexpected error
                                alert.setTitle("ERROR");
                                alert.setType(CustomAlert.MESSAGE);
                                alert.setContent("An error occurred while placing your order. Please try again");
                                alert.setButtonText("OK, GOT IT");

                            } else {
//                            successfully saved

                                alert.setTitle("CONGRATULATION");
                                alert.setContent("Your items are already on their way. You can track it anytime from Purchase section");
                                alert.setButtonText("OK, GOT IT");
                                alert.setType(CustomAlert.MESSAGE);
                                alert.setOnDismissed(new CustomAlert.OnDismiss() {
                                    @Override
                                    public void dismissed() {
                                        getActivity().finish();
                                    }
                                });

                            }
                        }
                    });

                } else
                    Toast.makeText(getContext(), "Please fill out the card information", Toast.LENGTH_SHORT).show();


            }
        });
    }

    boolean validateInputs() {
        boolean card_empty = layout.etCardNumber.getText().toString().isEmpty();
        boolean expiry_empty = layout.etExpiry.getText().toString().isEmpty();
        boolean cvc_empty = layout.etCvc.getText().toString().isEmpty();

        return !card_empty && !expiry_empty && !cvc_empty;
    }


}

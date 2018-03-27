package com.mirka.app.ghmarket.misc;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BindingViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding binding;

    public BindingViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);


    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
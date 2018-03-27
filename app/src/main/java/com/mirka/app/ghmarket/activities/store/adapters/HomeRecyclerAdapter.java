package com.mirka.app.ghmarket.activities.store.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mirka.app.ghmarket.DB.Category;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.RecyclerItemCategoryGroupBinding;
import com.mirka.app.ghmarket.misc.BindingViewHolder;
import com.mirka.app.ghmarket.misc.LinearSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Foster on 3/22/2018.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    public static final int VERTICAL_SPACING = 16;
    public static final int MAX_RESULTS_PER_ROW = 5;
    Context context;
    List<Category> categories = new ArrayList<>();


    public HomeRecyclerAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }


//    private void fetchCategories() {
//
////        view model gets data
//        Category.getCategories(new Category.OnComplete() {
//            @Override
//            public void complete(List<Category> cat, Exception e) {
//                if (cat!= null || cat.size() >0){
//                    categories = cat;
//                    notifyDataSetChanged();
//                }
//
//            }
//        });
//
//    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        bind(((RecyclerItemCategoryGroupBinding) holder.getBinding()), categories.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.recycler_item_category_group;
    }

    @Override
    public int getItemCount() {

        return categories == null ? 0 : categories.size();
    }


    void bind(RecyclerItemCategoryGroupBinding holder, Category category) {
        holder.tvTitle.setText(category.getTitle());
        holder.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler.setAdapter(new HomeGroupRecyclerAdapter(category.getProducts(), context));
        holder.recycler.addItemDecoration(new LinearSpacingItemDecoration(16, LinearSpacingItemDecoration.HORIZONTAL, false));
    }


}

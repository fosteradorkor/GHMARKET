package com.mirka.app.ghmarket.misc;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirka.app.ghmarket.DB.Product;
import com.mirka.app.ghmarket.R;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Foster on 3/24/2018.
 */

public class BindingAdapters {
    /**
     * adds the cedi symbol to the cost
     *
     * @param tv
     * @param cost
     */
    @BindingAdapter("cost")
    public static void setCost(TextView tv, double cost) {
        tv.setText("₵" + cost);
    }


    @BindingAdapter("imgUrl")
    public static void setImage(ImageView imageView, String url) {
        Picasso.get().load(url).into(imageView);
    }

    @BindingAdapter("carouselImgs")
    public static void setCarousel(CarouselView carouselView, Object imgs) {
        final List<String> images = new ArrayList<>();
        carouselView.setPageCount(images.size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(images.get(position)).into(imageView);
            }
        });
    }

    @BindingAdapter("actualCost")
    public static void setActualCost(TextView textView, Product product) {
        if (product != null) {
            Context c = textView.getContext();
            textView.setText(c.getString(R.string.ghc, new DecimalFormat("#.00").format(product.getPrice())));
            if (product.getDiscountedPrice() > 0)
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    @BindingAdapter("discountedPrice")
    public static void setDiscountedPrice(TextView textView, Product product) {
        if (product != null) {
            Context c = textView.getContext();
            textView.setText(
                    c.getString(R.string.ghc, new DecimalFormat("#.00").format(product.getDiscountedPrice()))
            );
        }
    }
}

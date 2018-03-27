package com.mirka.app.ghmarket.misc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirka.app.ghmarket.DB.Order;
import com.mirka.app.ghmarket.DB.User;
import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.activities.account.AccountActivity;
import com.mirka.app.ghmarket.activities.checkout.CheckoutActivity;
import com.mirka.app.ghmarket.databinding.LayoutSlideBinding;
import com.synnapps.carouselview.ViewListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Foster on 3/20/2018.
 */

public class Util {
    public static final int[] MAIN_TEXT = {R.string.walkthrough_text_1, R.string.walkthrough_text_2};
    public static final int[] MAIN_IMAGE = {R.drawable.slide_1, R.drawable.slide_2};


    //    slider listener
    public static ViewListener getViewListener(final Context context) {
        return new ViewListener() {
            @Override
            public View setViewForPosition(int position) {

                View slide = LayoutInflater.from(context).inflate(R.layout.layout_slide, null);
                LayoutSlideBinding bind = DataBindingUtil.bind(slide);

                bind.image.setImageResource(MAIN_IMAGE[position]);
                bind.txtSlideMain.setText(MAIN_TEXT[position]);

                return slide;
            }


        };

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    @SuppressLint("RestrictedApi")
    public static void disableShifMOde(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(false);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(false);

            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
    }

    public static void centerToolbarTitle(final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER_HORIZONTAL);
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            int i = toolbar.getMenu().size() * 60;
            layoutParams.setMargins(60, 0, 60, 0);
            toolbar.requestLayout();
        }
    }

    public static void setUpToolbar(final Toolbar toolbar) {
        if (toolbar.getMenu().size() == 0)
            toolbar.inflateMenu(R.menu.more);


        MenuItem item = toolbar.getMenu().getItem(0);
        final Drawable icon = item.getIcon();
        Util.centerToolbarTitle(toolbar);
        //context menu click listeners
//        toolbar.setOnMenuItemClickListener(toolBarNavItemActions(toolbar.getContext()));

        Order.getBag(new Order.OnComplete() {
            @Override
            public void complete(Order order, Exception exception) {
                if (order != null)
                    if (order.getProducts().size() > 0)

                        setBadgeCount(toolbar.getContext(), (LayerDrawable) icon, String.valueOf(order.getProducts().size()));

            }
        });

    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);

    }

    public static Toolbar.OnMenuItemClickListener toolBarNavItemActions(final Activity context) {
        return new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_bag:
                        context.startActivity(new Intent(context, CheckoutActivity.class));
                        return true;
                    case R.id.context_logout:
                        User.logOut();
                        context.finish();
                        return true;
                    case R.id.context_account:
                        context.startActivity(new Intent(context, AccountActivity.class));
                        return true;
                    case R.id.context_purchases:
                        return true;
                }

                return false;
            }
        };
    }
}
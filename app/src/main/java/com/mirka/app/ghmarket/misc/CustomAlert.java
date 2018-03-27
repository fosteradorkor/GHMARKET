package com.mirka.app.ghmarket.misc;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.mirka.app.ghmarket.R;
import com.mirka.app.ghmarket.databinding.FragmentCustomAlertBinding;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomAlert {

    public static final int MESSAGE = 1;
    public static final int PROGRESS = 0;
    FragmentCustomAlertBinding layout;
    DialogPlusBuilder dialog;
    DialogPlus _dialog;
    private OnDismiss ondismiss;

    public CustomAlert(Context context) {
        int _margin = context.getResources().getInteger(R.integer.dialog_margin);

        dialog = DialogPlus
                .newDialog(context)
                .setMargin(_margin, 0, _margin, 0)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.fragment_custom_alert))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if(ondismiss==null){
                            if (view.getId() == R.id.btn_confirm)
                                dialog.dismiss();
                        }else{
                            dialog.dismiss();
                            ondismiss.dismissed();
                        }

                    }
                })
                .setCancelable(false);

        _dialog = dialog.create();
        layout = DataBindingUtil.bind(dialog.getHolder().getInflatedView());
    }

    public CustomAlert show() {
        _dialog.show();
        return this;
    }

    public void dismiss() {
        _dialog.dismiss();
    }

    public CustomAlert setType(int type) {
        layout.switcher.setDisplayedChild(type);
        return this;
    }

    public CustomAlert setTitle(String title) {

        layout.tvTitle.setText(title);
        return this;
    }

    public CustomAlert setContent(String content) {
        layout.tvContent.setText(content);
        return this;

    }

    public CustomAlert setButtonText(String text) {
        layout.btnConfirm.setText(text);
        return this;
    }

    public void setOnDismissed(OnDismiss onDismissed){
        this.ondismiss =onDismissed;
    }


    public interface OnDismiss {
        void dismissed();
    }
}

package com.example.stiqr_java.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.example.stiqr_java.R;

public  class DialogNotif {
    Context context;
    String message;

    public static void DialogShower(Context context, String message) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        TextView tv_message = dialog.findViewById(R.id.tv_message);
        tv_message.setText(message);
        new Handler().postDelayed(() -> {
            dialog.dismiss();
        }, 2000);
        dialog.show();
    }

}

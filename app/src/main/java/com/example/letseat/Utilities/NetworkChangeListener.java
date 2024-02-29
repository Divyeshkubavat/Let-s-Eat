package com.example.letseat.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.letseat.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Comon.isConnectedToInternet(context))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_inflate = LayoutInflater.from(context).inflate(R.layout.internet_check,null);
            builder.setView(layout_inflate);
            Button retry = layout_inflate.findViewById(R.id.retry_button);
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            builder.setCancelable(false);
            dialog.show();
            dialog.getWindow().setGravity(Gravity.CENTER);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context,intent);

                }
            });
        }
    }
}

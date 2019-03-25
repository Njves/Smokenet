package com.example.egor.smokenet.Models;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.egor.smokenet.R;
//Класс для показа прогресс диалога
public class ProgressDialogShower {
    private static final String TAG = "ProgressDialogShower";
    private ProgressDialog progressDialog;
    private Context context;
    public ProgressDialogShower(Context context)
    {
        this.context = context;
    }
    public void showProgressDialog()
    {
        if(context!=null) {
            progressDialog = new ProgressDialog(context, R.style.MyTheme);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
        }else
        {
            Log.i(TAG, "Context has been not init");
        }

    }
    public void hideProgressDialog()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

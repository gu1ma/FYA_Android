package io.klineapps.fya.activity.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public boolean shouldAnimate = true;
    private ProgressDialog mProgressDialog;
    private AlertDialog toastDialog;



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected void goToActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    protected void goToActivity(Intent intent) {
        startActivity(intent);
    }

    protected void showToast(String text) {
        if (toastDialog != null) {
            try {
                toastDialog.dismiss();
            } catch (Exception e) {
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(text);
        builder.setPositiveButton("OK", null);
        toastDialog = builder.create();
        toastDialog.show();
    }

    protected void showToastWithTitle(String title, String message) {
        if (toastDialog != null) {
            try {
                toastDialog.dismiss();
            } catch (Exception e) {
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        toastDialog = builder.create();
        toastDialog.show();


    }


//    protected void showConnectionError(View coordinatorLayout, View.OnClickListener listener) {
//        Snackbar.make(coordinatorLayout, "Erro de conexão", Snackbar.LENGTH_INDEFINITE)
//                .setAction("Erro de conexão", listener)
//                .show();
//    }

    protected void showProgressDialog(Context context, String message) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        try {
            mProgressDialog.dismiss();
        } catch (Exception e) {
        }


    }

}






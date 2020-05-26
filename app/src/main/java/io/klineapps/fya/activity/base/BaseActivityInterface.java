package io.klineapps.fya.activity.base;

import android.content.Intent;

public interface BaseActivityInterface {


    void showToast(String text);

    void showToastWithTitle(String title, String message);

    void goToActivity(Class<?> activity);

    void goToActivity(Intent intent);

    void finishActivity();

    interface NormalView extends BaseActivityInterface {

    }

    interface BaseProgressView extends BaseActivityInterface {
//        void setNoConnectionLayoutVisibility(int visibility);

         void setNoConnectionVisibility(int visibility);

        void showProgressDialog(String message);

        void dismissProgressDialog();
    }
}
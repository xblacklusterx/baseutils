package com.dda.baseutils.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dda.baseutils.R;
import com.dda.baseutils.common.Logger;
import com.dda.baseutils.io.Preferences;

public class BaseActivity extends AppCompatActivity {

    private String preferencesName = "preferences";

    private Context context;

    public MaterialDialog progressDialog;

    public void onCreate(Bundle savedInstanceState, int layout, boolean showToolbar, boolean enableScreenshots) {
        super.onCreate(savedInstanceState);
        if (!showToolbar) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }

        context = this;

        if (!enableScreenshots)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(layout);
    }

    public void onCreate(Bundle savedInstanceState, int layout, boolean showToolbar) {
        onCreate(savedInstanceState, layout, showToolbar, true);
    }

    public void onCreate(Bundle savedInstanceState, int layout) {
        onCreate(savedInstanceState, layout, true);
    }

    public void showScreen(Class<?> destiny) {
        showScreen(destiny, false, null);
    }

    public void showScreen(Class<?> destiny, Bundle params) {
        showScreen(destiny, false, params);
    }

    public void showScreen(Class<?> destiny, boolean deleteStack) {
        showScreen(destiny, deleteStack, null);
    }

    public void showScreen(Class<?> destiny, boolean deleteStack, Bundle params) {
        Intent intent = new Intent(this, destiny);
        if (params != null) {
            intent.putExtras(params);
        }
        if (deleteStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
    }

    public void showScreenForResult(Class<?> destiny, Bundle params, int resultCode) {
        Intent intent = new Intent(this, destiny);
        if (params != null) {
            intent.putExtras(params);
        }

        startActivityForResult(intent, resultCode);
    }

    /**     Info        **/
    public void i(String string) {
        Logger.i(string);
    }

    public void i(String tag, String string) {
        Logger.i(tag, string);
    }

    /**     Error       **/
    public static void e(String string) {
        Logger.e(string);
    }

    public static void e(String tag, String string) {
        Logger.e(tag, string);
    }

    /**     Debug       **/
    public static void d(String string) {
        Logger.d(string);
    }

    public static void d(String tag, String string) {
        Logger.d(tag, string);
    }

    /**     Verbose     **/
    public static void v(String string) {
        Logger.v(string);
    }

    public static void v(String tag, String string) {
        Logger.v(tag, string);
    }

    /**     Warning     **/
    public static void w(String string) {
        Logger.w(string);
    }

    public static void w(String tag, String string) {
        Logger.w(tag, string);
    }

    public void showProgress(String title, String message) {
        try {
            progressDialog = new MaterialDialog.Builder(this)
                    .backgroundColor(getColorFromId(R.color.white))
                    .title(title)
                    .titleColor(getColorFromId(R.color.colorPrimary))
                    .content(message)
                    .contentColor(getColorFromId(R.color.text))
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .build();
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgress(String title, String message, int titleColor, int messageColor) {
        progressDialog = new MaterialDialog.Builder(this)
                .backgroundColor(getColorFromId(R.color.white))
                .title(title)
                .titleColor(getColorFromId(titleColor))
                .content(message)
                .contentColor(getColorFromId(messageColor))
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .build();
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void showGenericProgress() {
        showProgress(getString(R.string.title_loading), getString(R.string.txt_wait));
    }

    public void showGenericProgress(int titleColor, int messageColor) {
        showProgress(getString(R.string.title_loading), getString(R.string.txt_wait), titleColor, messageColor);
    }

    public void showLoadingProgress(String message) {
        showProgress(getString(R.string.title_loading), message);
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void finishProgressWithMessage(String title, String message, int titleColor) {
        hideProgress();
        showAlert(title, message, titleColor);
    }

    public void showAlert(String title, String message, int titleColor) {
        showAlert(title, message, titleColor, R.color.text, titleColor);
    }

    public void showAlert(String title, String message, int titleColor, int messageColor, int buttonColor) {
        MaterialDialog alert = new MaterialDialog.Builder(this)
                .backgroundColor(getColorFromId(R.color.white))
                .title(title)
                .titleColor(getColorFromId(titleColor))
                .content(message)
                .contentColor(getColorFromId(messageColor))
                .positiveText(getString(R.string.accept))
                .positiveColor(getColorFromId(buttonColor))
                .show();

        //alert.getActionButton(DialogAction.POSITIVE).setAllCaps(false);
    }

    public void showDismissActionAlert(String title, String message, int titleColor, DialogInterface.OnDismissListener listener) {
        new MaterialDialog.Builder(this)
                .backgroundColor(getColorFromId(R.color.white))
                .title(title)
                .titleColor(getColorFromId(titleColor))
                .content(message)
                .contentColor(getColorFromId(R.color.text))
                .positiveText(getString(R.string.accept))
                .positiveColor(getColorFromId(titleColor))
                .dismissListener(listener)
                .show();
    }

    public void showYesNoAlert(
            String title,
            String message,
            String yesTxt,
            String noTxt,
            int yesColor,
            int noColor,
            MaterialDialog.SingleButtonCallback yesAction,
            MaterialDialog.SingleButtonCallback noAction) {

        if (noAction == null) {
            noAction = new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                }
            };
        }

        new MaterialDialog.Builder(this)
                .backgroundColor(getColorFromId(R.color.white))
                .title(title)
                .titleColor(getColorFromId(R.color.colorPrimaryDark))
                .content(message)
                .contentColor(getColorFromId(R.color.text))
                .positiveText(yesTxt)
                .positiveColor(getColorFromId(yesColor))
                .onPositive(yesAction)
                .negativeText(noTxt)
                .negativeColor(getColorFromId(noColor))
                .onNegative(noAction)
                .show();
    }

    public int getColorFromId(int color) {
        return ContextCompat.getColor(context, color);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openLinkInBrowser(String url) {
        i("openLinkInBrowser, url = " + url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public boolean hasWritePermission() {
        if (context != null) {
            i("value of permission = " + ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE));
            return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }

        return false;
    }

    public void sendMediaBroadcast(Uri path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(path);
        sendBroadcast(mediaScanIntent);
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        toast.show();
    }

    public void setStringData(String key, String value) {
        Preferences.getInstance(context, preferencesName).setStringData(key, value);
    }

    public void setIntData(String key, int value) {
        Preferences.getInstance(context, preferencesName).setIntData(key, value);
    }

    public String getStringData(String key) {
        return Preferences.getInstance(context, preferencesName).getStringData(key);
    }

    public int getIntData(String key) {
        return Preferences.getInstance(context, preferencesName).getIntData(key);
    }

    public void removeStringData(String key) {
        Preferences.getInstance(context, preferencesName).removeStringData(key);
    }

    public void removeIntData(String key) {
        Preferences.getInstance(context, preferencesName).removeIntData(key);
    }

    public void setPreferencesName(String preferencesName) {
        this.preferencesName = preferencesName;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}


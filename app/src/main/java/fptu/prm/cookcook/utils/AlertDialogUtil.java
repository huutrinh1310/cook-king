package fptu.prm.cookcook.utils;

import android.content.Context;

import com.developer.kalert.KAlertDialog;

import fptu.prm.cookcook.R;

public class AlertDialogUtil {
    public static KAlertDialog kAlertDialog;

    private AlertDialogUtil() {
    }

    public static void loading(Context context) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.PROGRESS_TYPE)
                .setTitleTextSize(20);
        kAlertDialog.show();
    }

    public static void stop(Context context) {
        if (kAlertDialog != null) {
            kAlertDialog.dismissWithAnimation();
        }
    }

    public static void success(Context context, String title, String content, String btnContent, KAlertDialog.KAlertClickListener listener) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(btnContent, listener)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_alert);
        kAlertDialog.show();
    }

    public static void error(Context context, String title, String content, String btnContent, KAlertDialog.KAlertClickListener listener) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(btnContent, listener)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_alert);
        kAlertDialog.show();
    }

    public static void warning(Context context, String title, String content, String btnContent, KAlertDialog.KAlertClickListener listener) {
        kAlertDialog = new KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(btnContent, listener)
                .setContentTextSize(15)
                .setTitleTextSize(20)
                .confirmButtonColor(R.drawable.button_alert);
        kAlertDialog.show();
    }
}

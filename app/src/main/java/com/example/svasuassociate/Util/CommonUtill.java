package com.example.svasuassociate.Util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.regex.Pattern;

public class CommonUtill {

    private static final String EMAIL_PATTERN = "^[a-z0-9_]+(?:\\.[a-z0-9]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
    private static final String PHONE_NUMBER = "^(([0-9+]{6,20}))$";


    public static void openBrowser(Activity activity, String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadApp(Activity act, String pkg) {
        try {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkg)));
        } catch (android.content.ActivityNotFoundException anfe) {
            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pkg)));
        }
    }

    public static void shareApp(Activity act, String msg, String app) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                msg + ": " + "https://play.google.com/store/apps/details?id=" + app);
        sendIntent.setType("text/plain");
        act.startActivity(sendIntent);
    }

    public static boolean isValidEmail(String email) {
        return (Pattern.compile(EMAIL_PATTERN).matcher(email).matches());
    }
    public static boolean isValidPhoneNumber(String phone) {
//        String num = "0123456789";
        if (phone.indexOf("+") > 0)
            return false;
        return (Pattern.compile(PHONE_NUMBER).matcher(phone).matches());
    }

}

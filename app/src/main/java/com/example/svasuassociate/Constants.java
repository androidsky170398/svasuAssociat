package com.example.svasuassociate;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Constants {

    public static final String PREF_KEY = "superApp";
    public static final String LOGINKEY = "loginkey";
    public static final String FCM_KEY = "fcm_key";
    public static final String API_TOKEN = "api_token";
    public static final String USER_STATUS = "user_status";
    public static final String City_ID = "city_id";
    public static Toast toast;


    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sp = initializeSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getSavedPreferences(Context context, String key, String defValue) {
        SharedPreferences sp = initializeSharedPreferences(context);
        return sp.getString(key, defValue);
    }


    private static SharedPreferences initializeSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE);
    }

    public static boolean clearSavedPreferences(Context context, String key) {
        SharedPreferences sp = initializeSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean clearAllSavedPreferences(Context context) {
        SharedPreferences sp = initializeSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit().clear();
        return editor.commit();
    }


    /* To display toast*/
    public static void showCustomToastInCenter(Activity context, String message) {
        LayoutInflater inflater = (context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_grey, null, false);

       // LinearLayout toast_layout_root = layout.findViewById(R.id.toast_layout_root);
        TextView text = layout.findViewById(R.id.customToastText);
        text.setText(message);
        if (toast != null && toast.getView().isShown()) {
        } else {
            toast = new Toast(context);
           toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 110);

         //   toast_layout_root.setBackgroundColor(context.getResources().getColor(R.color.black));
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);
        }
    }


}

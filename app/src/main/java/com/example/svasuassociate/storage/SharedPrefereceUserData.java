package com.example.svasuassociate.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefereceUserData {
    Context context;
    String Key;
    SharedPreferences sharedPreferences;

    public SharedPrefereceUserData(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditorData() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        return editor;
    }

    public SharedPreferences getSharedData() {
        return sharedPreferences;
    }
    public SharedPreferences.Editor getRemove() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        return editor;
    }

}

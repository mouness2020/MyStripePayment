package com.example.mystripepayment;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {

    private static MySharedPreference yourPreference;
    private SharedPreferences sharedPreferences;

    public static MySharedPreference getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new MySharedPreference(context);
        }
        return yourPreference;
    }

    private MySharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("MySharedPreference",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, null);
        }
        return null;
    }
}

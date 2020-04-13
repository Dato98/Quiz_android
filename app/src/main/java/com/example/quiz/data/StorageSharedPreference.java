package com.example.quiz.data;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageSharedPreference implements Storage {

    private static final String RESULT_FILE = "last_result_info";


    @Override
    public void save(Context context, String key, String value) {
        SharedPreferences sharedPref = getInstance(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    @Override
    public String get(Context context, String key) {
        SharedPreferences sharedPreferences = getInstance(context);
        return sharedPreferences.getString(key,null);
    }

    @Override
    public boolean exists(Context context, String key) {
        return get(context,key) != null;
    }

    private SharedPreferences getInstance(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(RESULT_FILE,Context.MODE_PRIVATE);
        return  sharedPreferences;
    }
}

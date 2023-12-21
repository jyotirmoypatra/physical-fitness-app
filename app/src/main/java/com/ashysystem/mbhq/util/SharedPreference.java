package com.ashysystem.mbhq.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class SharedPreference {


    private SharedPreferences sharedPreferences;
    private Gson gson;


    private SharedPreferences prefs;
    Context mctx;
    private static final String PREF_NAME = "LocalFiltersPref";
    private static final String FILTER_KEY = "aaa";

    public SharedPreference(Context ctx) {
        // TODO Auto-generated constructor stub
        mctx = ctx;

    }

    public void write(String key, String putId, String value) {
        if (key != null && putId != null && value != null) {
            prefs = mctx.getSharedPreferences(key, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putString(putId, value);
            editor.commit();
        } else {
            Log.e("SHAREDPREFERENCE", "WRITE VALUE NULL STRING");
        }
    }

    public void writeBoolean(String key, String putId, Boolean value) {
        if (key != null && putId != null && value != null) {
            prefs = mctx.getSharedPreferences(key, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putBoolean(putId, value.booleanValue());
            editor.commit();
        } else {
            Log.e("SHAREDPREFERENCE", "WRITE VALUE NULL BOOLEAN");
        }
    }

    public boolean readBoolean(String key, String putId) {
        boolean id = false;
        try {
            prefs = mctx.getSharedPreferences(key, 0);
            id = prefs.getBoolean(putId, false);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String read(String key, String putId) {
        String id = "";
        try {
            prefs = mctx.getSharedPreferences(key, 0);
            id = prefs.getString(putId, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        if (context != null && preferenceFileName != null && serializedObjectKey != null && object != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            final Gson gson = new Gson();
            String serializedObject = gson.toJson(object);
            sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
            sharedPreferencesEditor.apply();
        } else {
            Log.e("SHAREDPREFERENCE", "WRITE VALUE NULL OBJECT");
        }
    }

    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }

    public void clear(String key) {
        prefs = mctx.getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }




    public void saveLocalFilters(Context context, String preferenceFileName, String serializedObjectKey, HashMap<String, ArrayList<String>> localFilters) {

        if (context != null && preferenceFileName != null && serializedObjectKey != null && localFilters != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            final Gson gson = new Gson();

            // Convert the HashMap to a Type using TypeToken for proper Gson serialization
            Type type = new TypeToken<HashMap<String, ArrayList<String>>>() {
            }.getType();
            String serializedObject = gson.toJson(localFilters, type);

            sharedPreferencesEditor.putString(serializedObjectKey, serializedObject); // Use serializedObjectKey here
            sharedPreferencesEditor.apply();
        } else {
            Log.e("SHAREDPREFERENCE", "WRITE VALUE NULL OBJECT");
        }


    }


    public HashMap<String, ArrayList<String>> getLocalFilters(Context context, String preferenceFileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        // Retrieve the serialized object using the provided key
        String serializedObject = sharedPreferences.getString(FILTER_KEY, null);

        if (serializedObject != null) {
            // Deserialize the JSON string back to the HashMap<String, ArrayList<String>> type
            Type type = new TypeToken<HashMap<String, ArrayList<String>>>() {
            }.getType();
            return gson.fromJson(serializedObject, type);
        }
        return null;

    }

    public void clearLocalFilters() {
        sharedPreferences.edit().remove(FILTER_KEY).apply();
    }
}
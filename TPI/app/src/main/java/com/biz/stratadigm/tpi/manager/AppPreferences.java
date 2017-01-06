package com.biz.stratadigm.tpi.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.annimon.stream.Optional;


public class AppPreferences {
    private static final String FILE_NAME = "app_preferences";

    private static final String PREF_TOKEN = "token";

    private final SharedPreferences preferences;

    public AppPreferences(Context applicationContext) {
        preferences = applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    public Optional<String> getToken() {
        return Optional.ofNullable(preferences.getString(PREF_TOKEN, null));
    }

    public void setToken(@Nullable String token) {
        setStringPref(PREF_TOKEN, token);
    }

    private void setStringPref(String prefName, String prefValue) {
        preferences.edit()
                .putString(prefName, prefValue)
                .apply();
    }
}

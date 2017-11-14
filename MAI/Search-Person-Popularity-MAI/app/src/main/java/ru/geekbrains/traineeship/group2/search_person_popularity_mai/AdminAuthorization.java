package ru.geekbrains.traineeship.group2.search_person_popularity_mai;

import android.content.Context;

/**
 * Created by skubatko on 06/11/17
 */

public class AdminAuthorization {

    /**
     * This method makes AdminModel authorized
     *
     * @param context current context
     */
    public static void setAuthorized( Context context ) {
        context.getSharedPreferences( Constants.LOGIN_PREFERENCES, context.MODE_PRIVATE )
                .edit()
                .putBoolean( Constants.PREFERENCES_AUTHORIZED_KEY, true )
                .apply();
    }

    /**
     * This method makes AdminModel unauthorized
     *
     * @param context current context
     */
    public static void setNotAuthorized( Context context ) {
        context.getSharedPreferences( Constants.LOGIN_PREFERENCES, context.MODE_PRIVATE )
                .edit()
                .putBoolean( Constants.PREFERENCES_AUTHORIZED_KEY, false )
                .apply();
    }

    /**
     * This method checks if AdminModel is authorized
     *
     * @param context current context
     * @return {@code true} if the user is authorized and {@code false} if not
     */
    public static boolean isNotAuthorized( Context context ) {
        return !context.getSharedPreferences( Constants.LOGIN_PREFERENCES, context.MODE_PRIVATE )
                .getBoolean( Constants.PREFERENCES_AUTHORIZED_KEY, false );
    }
}

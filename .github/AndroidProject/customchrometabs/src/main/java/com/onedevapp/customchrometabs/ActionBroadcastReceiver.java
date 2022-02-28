package com.onedevapp.customchrometabs;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * A BroadcastReceiver that handles the Action Intent from the Custom Tab and shows the Url
 * in a Toast.
 */
public class ActionBroadcastReceiver extends BroadcastReceiver {
    public static final String KEY_ACTION_SOURCE = "org.chromium.customtabsdemos.ACTION_SOURCE";
    public static final int ACTION_ACTION_BUTTON = 1;
    public static final int ACTION_MENU_ITEM = 2;
    public static final int ACTION_TOOLBAR = 3;

    @Override
    public void onReceive(Context context, Intent intent) {

        ActionItemCallback listener = (ActionItemCallback) context;  // initialse
        String url = intent.getDataString();
        if (url != null) {
            listener.performedAction(intent.getIntExtra(KEY_ACTION_SOURCE, -1));
            /*String toastText =
                    getToastText(context, intent.getIntExtra(KEY_ACTION_SOURCE, -1), url);
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();*/
        }
    }

    /*private String getToastText(Context context, int actionId, String url) {
        switch (actionId) {
            case ACTION_ACTION_BUTTON:
                return "ACTION_ACTION_BUTTON clicked";
            case ACTION_MENU_ITEM:
                return "ACTION_MENU_ITEM clicked";
            case ACTION_TOOLBAR:
                return "ACTION_TOOLBAR clicked";
            default:
                return "Unknown ACTION_BUTTON clicked";
        }
    }*/
}
package com.onedevapp.customchrometabs;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentActivity;

public class CustomHeadlessActivity  extends FragmentActivity implements ActionItemCallback{

    private static final int TOOLBAR_ITEM_ID = 1;

    private String urlToLaunch = "";
    private String colorString = "";
    private String secondaryColorString = "";
    private String  actionLabel = "";
    private String  menuItemTitle = "";
    private boolean showTitle = false;
    private boolean urlBarHiding = false;
    private boolean showActionBtn = false;
    private boolean addMenuItem = false;
    private boolean addDefaultShareItem = false;
    private boolean addToolbarItem = false;
    private boolean customBackButton = false;

    private boolean mCustomTabsOpened = false;

    private CustomTabActivityHelper mCustomTabActivityHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCustomTabActivityHelper = new CustomTabActivityHelper();

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if (intent == null || intent.getExtras() == null) return;

        if (intent.getExtras().containsKey("urlToLaunch")) {
            urlToLaunch = intent.getExtras().getString("urlToLaunch");
        }
        if (intent.getExtras().containsKey("colorString")) {
            colorString = intent.getExtras().getString("colorString");
        }
        if (intent.getExtras().containsKey("secondaryColorString")) {
            secondaryColorString = intent.getExtras().getString("secondaryColorString");
        }
        if (intent.getExtras().containsKey("actionLabel")) {
            actionLabel = intent.getExtras().getString("actionLabel");
        }
        if (intent.getExtras().containsKey("menuItemTitle")) {
            menuItemTitle = intent.getExtras().getString("menuItemTitle");
        }
        if (intent.getExtras().containsKey("showTitle")) {
            showTitle = intent.getExtras().getBoolean("showTitle");
        }
        if (intent.getExtras().containsKey("urlBarHiding")) {
            urlBarHiding = intent.getExtras().getBoolean("urlBarHiding");
        }
        if (intent.getExtras().containsKey("showActionBtn")) {
            showActionBtn = intent.getExtras().getBoolean("showActionBtn");
        }
        if (intent.getExtras().containsKey("addMenuItem")) {
            addMenuItem = intent.getExtras().getBoolean("addMenuItem");
        }
        if (intent.getExtras().containsKey("addDefaultShareItem")) {
            addDefaultShareItem = intent.getExtras().getBoolean("addDefaultShareItem");
        }
        if (intent.getExtras().containsKey("addToolbarItem")) {
            addToolbarItem = intent.getExtras().getBoolean("addToolbarItem");
        }
        openCustomTab();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomTabActivityHelper.bindCustomTabsService(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCustomTabActivityHelper.unbindCustomTabsService(this);
    }

    private void openCustomTab() {

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        CustomTabColorSchemeParams.Builder defaultBuilder = new CustomTabColorSchemeParams.Builder();
        if(!colorString.isEmpty())
            defaultBuilder.setToolbarColor(Color.parseColor(colorString));
        if(!secondaryColorString.isEmpty())
            defaultBuilder.setSecondaryToolbarColor(Color.parseColor(secondaryColorString));
        CustomTabColorSchemeParams defaultColors = defaultBuilder.build();

        intentBuilder.setDefaultColorSchemeParams(defaultColors);

        if (showActionBtn) {
            //Generally you do not want to decode bitmaps in the UI thread. Decoding it in the
            //UI thread to keep the example short.
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    android.R.drawable.ic_menu_share);
            PendingIntent pendingIntent =
                    createPendingIntent(ActionBroadcastReceiver.ACTION_ACTION_BUTTON);
            intentBuilder.setActionButton(icon, actionLabel, pendingIntent);
        }

        if (addMenuItem) {
            PendingIntent menuItemPendingIntent =
                    createPendingIntent(ActionBroadcastReceiver.ACTION_MENU_ITEM);
            intentBuilder.addMenuItem(menuItemTitle, menuItemPendingIntent);
        }

        int shareState = addDefaultShareItem ?
                CustomTabsIntent.SHARE_STATE_ON : CustomTabsIntent.SHARE_STATE_OFF;
        intentBuilder.setShareState(shareState);

        if (addToolbarItem) {
            //Generally you do not want to decode bitmaps in the UI thread. Decoding it in the
            //UI thread to keep the example short.
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    android.R.drawable.ic_menu_share);
            PendingIntent pendingIntent =
                    createPendingIntent(ActionBroadcastReceiver.ACTION_TOOLBAR);
            intentBuilder.addToolbarItem(TOOLBAR_ITEM_ID, icon, actionLabel, pendingIntent);
        }

        intentBuilder.setShowTitle(showTitle);

        intentBuilder.setUrlBarHidingEnabled(urlBarHiding);

        /*if (customBackButton) {
            intentBuilder.setCloseButtonIcon(toBitmap(getDrawable(R.drawable.ic_arrow_back)));
        }*/

        intentBuilder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        mCustomTabsOpened = true;
        CustomTabActivityHelper.openCustomTab(
                this, intentBuilder.build(), Uri.parse(urlToLaunch), new WebViewFallback());
    }

    private PendingIntent createPendingIntent(int actionSourceId) {
        Intent actionIntent = new Intent(
                this.getApplicationContext(), ActionBroadcastReceiver.class);
        actionIntent.putExtra(ActionBroadcastReceiver.KEY_ACTION_SOURCE, actionSourceId);
        return PendingIntent.getBroadcast(
                getApplicationContext(), actionSourceId, actionIntent, 0);
    }

    /**
     * Return a Bitmap representation of the Drawable. Based on Android KTX.
     */
    private Bitmap toBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Rect oldBounds = new Rect(drawable.getBounds());

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(new Canvas(bitmap));

        drawable.setBounds(oldBounds);
        return bitmap;
    }

    @Override
    public void performedAction(int actionId) {
        switch (actionId) {
            case ActionBroadcastReceiver.ACTION_ACTION_BUTTON:
                Toast.makeText(CustomHeadlessActivity.this,  "ACTION_ACTION_BUTTON clicked",Toast.LENGTH_SHORT).show();
            case ActionBroadcastReceiver.ACTION_MENU_ITEM:
                Toast.makeText(this,   "ACTION_MENU_ITEM clicked",Toast.LENGTH_SHORT).show();
            case ActionBroadcastReceiver.ACTION_TOOLBAR:
                Toast.makeText(this,   "ACTION_TOOLBAR clicked",Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(this,   "Unknown ACTION_BUTTON clicked",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCustomTabsOpened) {
            mCustomTabsOpened = false;
            finish();
        }
    }
}
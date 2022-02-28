package com.onedevapp.customchrometabs;


import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;

public class CustomTabPlugin {
    // region Declarations
    private static final CustomTabPlugin instance = new CustomTabPlugin();

    public static CustomTabPlugin getInstance() {
        return instance;
    }

    private CustomTabPlugin() {
    }

    private WeakReference<Activity> mActivityWeakReference; //Activity references

    private String urlToLaunch = "";
    private String colorString = "";
    private String secondaryColorString = "";
    private boolean showTitle = false;
    private boolean urlBarHiding = false;

    //region Constructor
    //Private constructor with activity
    public CustomTabPlugin setActivity(Activity activity) {
        this.mActivityWeakReference = new WeakReference<>(activity);
        return this;
    }


    /**
     * Set the URL to Open
     *
     * @param url webpage to open
     * @return the android plugin instance
     */
    public CustomTabPlugin setUrl(String url) {
        this.urlToLaunch = url;
        return this;
    }

    /**
     * Set the Color for header
     *
     * @param colorString the header color
     * @return the android plugin instance
     */
    public CustomTabPlugin setColorString(String colorString) {
        this.colorString = colorString;
        return this;
    }

    /**
     * Set the Color for header
     *
     * @param secondaryColorString the header color
     * @return the android plugin instance
     */
    public CustomTabPlugin setSecondaryColorString(String secondaryColorString) {
        this.secondaryColorString = secondaryColorString;
        return this;
    }

    /**
     * Toggle title header
     *
     * @param showTitle
     * @return the android plugin instance
     */
    public CustomTabPlugin ToggleShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
        return this;
    }
    /**
     * Toggle url bar
     *
     * @param urlBarHiding
     * @return the android plugin instance
     */
    public CustomTabPlugin ToggleUrlBarHiding(boolean urlBarHiding) {
        this.urlBarHiding = urlBarHiding;
        return this;
    }

    // region helper functions

    /**
     * Returns the current activity
     */
    protected Activity getActivity() {
        return mActivityWeakReference.get();
    }
    //endregion

    public void openCustomTab() {
        getActivity().runOnUiThread(() -> {
            Intent intent = new Intent(getActivity(), CustomHeadlessActivity.class);
            intent.putExtra("urlToLaunch", urlToLaunch);
            intent.putExtra("colorString", colorString);
            intent.putExtra("secondaryColorString", secondaryColorString);
            intent.putExtra("showTitle", showTitle);
            intent.putExtra("urlBarHiding", urlBarHiding);
            getActivity().startActivity(intent);
        });
    }
}
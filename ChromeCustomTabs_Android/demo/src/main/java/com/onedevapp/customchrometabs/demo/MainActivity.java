package com.onedevapp.customchrometabs.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.onedevapp.customchrometabs.CustomTabPlugin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomTabPlugin customTabPlugin = CustomTabPlugin.getInstance();
        customTabPlugin.setActivity(this).setColorString("#FFFFFF").setUrl("http://www.google.com").ToggleShowTitle(false).ToggleUrlBarHiding(true).openCustomTab();
    }
}
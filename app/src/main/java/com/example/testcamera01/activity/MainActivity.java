package com.example.testcamera01.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.testcamera01.R;
import com.example.testcamera01.fragment.MainFragment;
import com.example.testcamera01.fragment.WebViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private MainFragment    mainFragment;
    private WebViewFragment webViewFragment;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        mainFragment = new MainFragment();  webViewFragment = new WebViewFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_app:
                        setFrag(0);
                        break;
                    case R.id.action_webview:
                        setFrag(1);
                        break;
                }
                return true;
            }
        });
        setFrag(0);
    }

    private void setFrag(int n){
        FragmentManager fm;
        FragmentTransaction ft;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 0:
                ft.replace(R.id.main_frame, mainFragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, webViewFragment);
                ft.commit();
                break;
        }
    }
}
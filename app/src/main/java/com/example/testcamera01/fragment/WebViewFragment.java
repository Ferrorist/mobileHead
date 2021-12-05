package com.example.testcamera01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testcamera01.R;

public class WebViewFragment extends Fragment {
    WebView webView;
    WebSettings Setting;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = view.findViewById(R.id.webview);
        Setting = webView.getSettings();
        Setting.setJavaScriptEnabled(true);
        Setting.setLoadWithOverviewMode(true);
        Setting.setUseWideViewPort(true);
        Setting.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(){});

        webView.loadUrl("https://diagnosehair.netlify.app/index.html");
        return view;
    }
}

package com.example.testcamera01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class selectedImageActivity extends AppCompatActivity {
    private String getImageUri;
    private ImageView selectedImage;
    private Button bt_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);
        selectedImage = findViewById(R.id.selectedimage);
        bt_selected = findViewById(R.id.bt_selected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        getImageUri = intent.getExtras().getString("imageString_cam");
        Bitmap bitmap;
        bt_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
package com.example.testcamera01.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testcamera01.R;

public class selectedImageActivity extends AppCompatActivity {
    private Bitmap bitmap;
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
        byte[] arr = getIntent().getByteArrayExtra("imagebyte");
        bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        selectedImage.setImageBitmap(bitmap);
        bt_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
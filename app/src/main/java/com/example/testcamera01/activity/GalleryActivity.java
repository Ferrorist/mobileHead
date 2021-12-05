package com.example.testcamera01.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.testcamera01.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class GalleryActivity extends AppCompatActivity {
    private Bitmap picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Intent intent = new Intent();
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    picture = BitmapFactory.decodeStream(is);
                    Intent intent = new Intent(GalleryActivity.this, selectedImageActivity.class);
                    intent.putExtra("image", picture);
                    startActivity(intent);
                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
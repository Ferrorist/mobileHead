package com.example.testcamera01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraActivity extends AppCompatActivity{
    private ImageView square;
    private Button  bt_capture;
    private TextView textView;
    CameraSurface surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        FrameLayout frame = findViewById(R.id.cam_frame);
        surfaceView = new CameraSurface(this);
        frame.addView(surfaceView);
        bt_capture = findViewById(R.id.bt_capture);
        textView = findViewById(R.id.cam_text);
        square = findViewById(R.id.square_view);
        bt_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_capture.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                square.setVisibility(View.INVISIBLE);
                takePicture();
              //  finish();
            }
        });

    }

    public void takePicture() {
        surfaceView.capture(new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap  bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                String outUriStr = MediaStore.Images.Media.insertImage(
                        getContentResolver(), bitmap, "Captured Image", "Captured Image using Camera."
                );
                if(outUriStr == null){
                    Log.d("SampleCapture", "Image insert failed.");
                    return;
                }
                else{
                    Uri outUri = Uri.parse(outUriStr);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                }
                surfaceView.camera.startPreview();
            }
        });
    }
}
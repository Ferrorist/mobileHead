package com.example.testcamera01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private String outUriStr;
    private CameraSurface surfaceView;

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
                Intent intent = new Intent(CameraActivity.this, selectedImageActivity.class);
                intent.putExtra("imageString_cam", outUriStr);
                startActivity(intent);
                finish();
            }
        });

    }

    public void takePicture() {
        surfaceView.capture(new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] data, Camera camera) { // 사진을 찍은 후 callback 되는 함수.
                Matrix matrix = new Matrix();   matrix.postRotate(90); // 회전 각도 조정.
                Bitmap  getImage = BitmapFactory.decodeByteArray(data, 0, data.length); // -90도로 회전되어 있는 상태.
                Bitmap  bitmap = Bitmap.createBitmap(getImage, 0, 0, getImage.getWidth(), getImage.getHeight(), matrix, false); // 90도를 더 회전시켜 0도로 만든 bitmap.
                outUriStr = MediaStore.Images.Media.insertImage(
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
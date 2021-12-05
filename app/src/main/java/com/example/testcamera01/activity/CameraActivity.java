package com.example.testcamera01.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testcamera01.R;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity{
    private ImageView square;
    private Button  bt_capture, bt_change;
    private TextView textView;
    private CameraSurface surfaceView;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        FrameLayout frame = findViewById(R.id.cam_frame);
        surfaceView = new CameraSurface(this);
        frame.addView(surfaceView);
        bt_capture = findViewById(R.id.bt_capture);
        bt_change = findViewById(R.id.bt_change_cam);
        textView = findViewById(R.id.cam_text);
        square = findViewById(R.id.square_view);
        bt_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { surfaceView.ChangeCamera(); }
        });
    }
    public void takePicture(){
        bt_capture.setVisibility(View.INVISIBLE);
        bt_change.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        square.setVisibility(View.INVISIBLE);
        cam_picture();
    }

    public void cam_picture() {
        surfaceView.capture(new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] data, Camera camera) { // 사진을 찍은 후 callback 되는 함수.
                Matrix matrix = new Matrix();    matrix.postRotate(-90); // 회전 각도 조정.
                Bitmap  getImage = BitmapFactory.decodeByteArray(data, 0, data.length); // 90도로 회전되어 있는 상태.
                Bitmap  bitmap = Bitmap.createBitmap(getImage, 0, 0, getImage.getWidth(), getImage.getHeight(), matrix, false); // -90도를 더 회전시켜 0도로 만든 bitmap.
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 72, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent = new Intent(CameraActivity.this, selectedImageActivity.class);
                intent.putExtra("imagebyte", byteArray);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
                takePicture();
                break;
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return false;
    }
}
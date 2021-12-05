package com.example.testcamera01.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class CameraThread extends Thread{
    private CameraSurface surfaceView;
    private AppCompatActivity activity;
    private Uri outUri = null;
    public CameraThread(CameraSurface surfaceView, AppCompatActivity activity){
        this.surfaceView = surfaceView;
        this.activity = activity;
    }

    public void run(){
        surfaceView.capture(new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] data, Camera camera) { // 사진을 찍은 후 callback 되는 함수.
                Matrix matrix = new Matrix();   matrix.postRotate(-90); // 회전 각도 조정.
                Bitmap getImage = BitmapFactory.decodeByteArray(data, 0, data.length); // 90도로 회전되어 있는 상태.
                Bitmap  bitmap = Bitmap.createBitmap(getImage, 0, 0, getImage.getWidth(), getImage.getHeight(), matrix, false); // -90도를 더 회전시켜 0도로 만든 bitmap.
                String outUriStr = MediaStore.Images.Media.insertImage(
                        activity.getContentResolver(), bitmap, "Captured Image", "Captured Image using Camera."
                );
                if(outUriStr == null){
                    Log.d("SampleCapture", "Image insert failed.");
                    return;
                }
                else{
                    outUri = Uri.parse(outUriStr);
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                }
            }
        });
    }

    public Uri getOutUri(){ return outUri; }
}
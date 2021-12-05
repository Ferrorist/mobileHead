package com.example.testcamera01.activity;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

class CameraSurface extends SurfaceView implements SurfaceHolder.Callback {
    private int CAM_CODE = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private SurfaceHolder holder;
    public Camera camera = null;
    public CameraSurface(Context context){
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        camera = Camera.open(CAM_CODE);
        camera.setDisplayOrientation(90); // preview 90도로 돌림.
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public boolean capture(Camera.PictureCallback handler){
        if(camera != null){
            camera.takePicture(null, null, handler);
            return true;
        }
        else    return false;
    }
    
    private int findFrontSideCamera() { // 전면 카메라 코드 확인.
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        
        for(int i = 0; i < numberOfCameras; i++){
            Camera.CameraInfo camInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, camInfo);
            if(camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                cameraId = i;   break;
            }
        }
        return cameraId;
    }

    public void ChangeCamera(){
        camera.stopPreview();
        camera.release();
        camera = null;
        if(CAM_CODE == Camera.CameraInfo.CAMERA_FACING_FRONT)
            CAM_CODE = Camera.CameraInfo.CAMERA_FACING_BACK;
        else {
            CAM_CODE = Camera.CameraInfo.CAMERA_FACING_FRONT;

        }
        camera = Camera.open(CAM_CODE);
        camera.startPreview();
    }
}

package com.saf217.mytestapp;

import java.io.IOException;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class RecorderService extends Service {

	private static final String TAG = "RecorderService";
 	private SurfaceView mSurfaceView;
 	private SurfaceHolder mSurfaceHolder;
 	private static Camera mServiceCamera;
 	private boolean mRecordingStatus;
 	private MediaRecorder mMediaRecorder;
 	private EditText et;
 	
 	@Override
 	public void onCreate() {
 		mRecordingStatus = false;
 		mServiceCamera = VideoActivity.mCamera;
 		mSurfaceView = VideoActivity.mSurfaceView;
 		mSurfaceHolder = VideoActivity.mSurfaceHolder;
 	    et = VideoActivity.et;
 		super.onCreate(); 		
 	}
 
 	@Override
 	public IBinder onBind(Intent intent) {
 		// TODO Auto-generated method stub
 		return null;
 	}
 	
 	@Override
 	public int onStartCommand(Intent intent, int flags, int startId) {
 		super.onStartCommand(intent, flags, startId);
 		if (mRecordingStatus == false)
 			startRecording();
 
 		return START_STICKY;
 	}
 
 	@Override
 	public void onDestroy() {
 		stopRecording();
 		mRecordingStatus = false;
 		
 		super.onDestroy();
 	}   
 
 	@SuppressWarnings("deprecation")
	public boolean startRecording(){
 	et.setText("should be this");
 		try {
 			Toast.makeText(getBaseContext(), "Recording Started", Toast.LENGTH_SHORT).show();
 			
 			mServiceCamera = Camera.open();
 			Camera.Parameters params = mServiceCamera.getParameters();
 			mServiceCamera.setParameters(params);
 			Camera.Parameters p = mServiceCamera.getParameters();
 			
 			final List<Size> listSize = p.getSupportedPreviewSizes();
 			Size mPreviewSize = listSize.get(2);
 			Log.v(TAG, "use: width = " + mPreviewSize.width +
 						 " height = " + mPreviewSize.height);
 			p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
 			p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
 			mServiceCamera.setParameters(p);
 
 			try {
 				mServiceCamera.setPreviewDisplay(mSurfaceHolder);
 				mServiceCamera.startPreview();
 			}
 			catch (IOException e) {
 				Log.e(TAG, e.getMessage());
 				e.printStackTrace();
 			}
 			
 			mServiceCamera.unlock();
 			
 			mMediaRecorder = new MediaRecorder();
 			mMediaRecorder.setCamera(mServiceCamera);
 			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
 			mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
 			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
 			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
 			mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
 			mMediaRecorder.setOutputFile("/storage/emulated/0/DCIM/Camera/blahhhhhhh.mp4");
 			mMediaRecorder.setVideoFrameRate(30);
 			mMediaRecorder.setVideoSize(mPreviewSize.width, mPreviewSize.height);
 			mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
 			
 			mMediaRecorder.prepare();
 			mMediaRecorder.start(); 
 
 			mRecordingStatus = true;
 			
 			return true;
 		} catch (IllegalStateException e) {
 			Log.d(TAG, e.getMessage());
 			e.printStackTrace();
 			return false;
 		} catch (IOException e) {
 			Log.d(TAG, e.getMessage());
 			e.printStackTrace();
 			return false;
 		}
 	}
 
 	public void stopRecording() {
 		Toast.makeText(getBaseContext(), "Recording Stopped", Toast.LENGTH_SHORT).show();
 		try {
 			mServiceCamera.reconnect();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		mMediaRecorder.stop();
 		mMediaRecorder.reset();
 		
 		mServiceCamera.stopPreview();
 		mMediaRecorder.release();
 		
 		mServiceCamera.release();
 		mServiceCamera = null;
 	}
}

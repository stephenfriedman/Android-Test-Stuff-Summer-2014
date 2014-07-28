package com.saf217.mytestapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class VideoActivity extends Activity implements SurfaceHolder.Callback{

	private static final String TAG = "Recorder";
	 	public static SurfaceView mSurfaceView;
	 	public static SurfaceHolder mSurfaceHolder;
	 	public static Camera mCamera;
	 	public static boolean mPreviewRunning;
	 	public static EditText et;
	 	
	 	/** Called when the activity is first created. */
	     @SuppressWarnings("deprecation")
		@Override
	     public void onCreate(Bundle savedInstanceState) {
	         super.onCreate(savedInstanceState);
	         getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	         setContentView(R.layout.activity_video);
	         
	 		mSurfaceView = (SurfaceView) findViewById(R.id.preview);
	 		mSurfaceHolder = mSurfaceView.getHolder();
	 		mSurfaceHolder.addCallback(this);
	 		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	 		
	 		Button btnStart = (Button) findViewById(R.id.btn_start);
	 		btnStart.setOnClickListener(new View.OnClickListener()
	 		{
	 			public void onClick(View v)
	 			{    				
	 				et = (EditText)findViewById(R.id.editTextTester);
	 				et.setText("Changed");
	 				Intent intent = new Intent(VideoActivity.this, RecorderService.class);
	 				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 				startService(intent);
	 			}
	 		});
	 
	 		Button btnStop = (Button) findViewById(R.id.btn_stop);
	 		btnStop.setOnClickListener(new View.OnClickListener()
	 		{
	 			public void onClick(View v)
	 			{
	 				stopService(new Intent(VideoActivity.this,RecorderService.class));
	 			}
	 		});
	     }
	     
	 	@Override
	 	public void surfaceCreated(SurfaceHolder holder) {
	 		try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
	 
	 	@Override
	 	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	 	}
	 	
	 	@Override
	 	public void surfaceDestroyed(SurfaceHolder holder) {
	 		// TODO Auto-generated method stub
	 		  // If your preview can change or rotate, take care of those events here.
	        // Make sure to stop the preview before resizing or reformatting it.

	        if (mSurfaceHolder.getSurface() == null){
	          // preview surface does not exist
	          return;
	        }

	        // stop preview before making changes
	        try {
	            mCamera.stopPreview();
	        } catch (Exception e){
	          // ignore: tried to stop a non-existent preview
	        }

	        // set preview size and make any resize, rotate or
	        // reformatting changes here

	        // start preview with new settings
	        try {
	            mCamera.setPreviewDisplay(mSurfaceHolder);
	            mCamera.startPreview();

	        } catch (Exception e){
	            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
	        }
	 	}

	
}

package com.saf217.mytestapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class RegularvideoActivity extends Activity implements SurfaceHolder.Callback {
	Button myButton;
	MediaRecorder mediaRecorder;
	SurfaceHolder surfaceHolder;
	boolean recording;
	int curBrightnessValue;
	int brightness = 255;
	ContentResolver cResolver;
	Window window;
	Context c;
	boolean videoing = false;


@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		mediaRecorder.stop();
		mediaRecorder.release();
		videoing=false;
		System.putInt(cResolver, System.SCREEN_BRIGHTNESS, curBrightnessValue);
		LayoutParams layoutpars = window.getAttributes();
		layoutpars.screenBrightness = curBrightnessValue;
		window.setAttributes(layoutpars);
	}


	   
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       c=this;
	       recording = false;
	      
	       mediaRecorder = new MediaRecorder();
	       initMediaRecorder();
	      
	       setContentView(R.layout.regularvideo);
	       getWindow().getDecorView().setBackgroundColor(Color.BLACK);
	       
	       SurfaceView myVideoView = (SurfaceView)findViewById(R.id.surfaceViewRegular);
	       surfaceHolder = myVideoView.getHolder();
	       surfaceHolder.addCallback(this);
	       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	      
	       myButton = (Button)findViewById(R.id.buttonRegularStop);
	       myButton.setOnClickListener(myButtonOnClickListener);
	       
	       
	       try {
			 curBrightnessValue = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	       cResolver = getContentResolver();

			window = getWindow();

			Settings.System.putInt(cResolver,
			Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	   }  
	   private Button.OnClickListener myButtonOnClickListener
	   = new Button.OnClickListener(){

	 @Override
	 public void onClick(View arg0) {
	  // TODO Auto-generated method stub
	  if(recording){
	   mediaRecorder.stop();
	   mediaRecorder.release();
	   videoing=false;
       System.putInt(cResolver, System.SCREEN_BRIGHTNESS, curBrightnessValue);
       LayoutParams layoutpars = window.getAttributes();
       layoutpars.screenBrightness = curBrightnessValue;
       window.setAttributes(layoutpars);
       String s="Recording captured and saved!";
       Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
       t.show();
       getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       Intent myIntent = new Intent(c,Fourth.class);
		startActivity(myIntent);	  
		// finish();
		
	  }else{
	   recording = true;
	   int brightness = 0;
	   videoing=true;
	   String s="Recording started!";
       Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
       t.show();
       System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
       LayoutParams layoutpars = window.getAttributes();
       layoutpars.screenBrightness = brightness;
       window.setAttributes(layoutpars);
          mediaRecorder.start();
         	  }
	 }};
	  
	
	 @Override
	 public boolean dispatchKeyEvent(KeyEvent event) {
	     int action = event.getAction();
	     int keyCode = event.getKeyCode();
	         switch (keyCode) {
	         case KeyEvent.KEYCODE_VOLUME_UP:
	             if (action == KeyEvent.ACTION_DOWN) {
	                 //TODO
	            	 if(recording==false)
	            	 {
	            		 recording = true;
	            		   int brightness = 0;
	            		   videoing=true;
	            		   String s="Recording started!";
	            	       Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
	            	       t.show();
	            	       System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
	            	       LayoutParams layoutpars = window.getAttributes();
	            	       layoutpars.screenBrightness = brightness;
	            	       window.setAttributes(layoutpars);
	            	          mediaRecorder.start();
	            	 }
	            	 else
	            	 {
	            	 mediaRecorder.stop();
	            	 mediaRecorder.release();
	            	 videoing=false;
	                 System.putInt(cResolver, System.SCREEN_BRIGHTNESS, curBrightnessValue);
	                 LayoutParams layoutpars = window.getAttributes();
	                 layoutpars.screenBrightness = curBrightnessValue;
	                 window.setAttributes(layoutpars);
	                 String s="Recording captured and saved!";
	                 Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
	                 t.show();
	                 Intent myIntent = new Intent(c,Fourth.class);
	          		startActivity(myIntent);
	            	 }
	             }
	             return true;
	         case KeyEvent.KEYCODE_VOLUME_DOWN:
	             if (action == KeyEvent.ACTION_DOWN) {
	                 //TODO
	            	 if(recording==false)
	            	 {
	            		 recording = true;
	            		   int brightness = 0;
	            		   videoing=true;
	            		   String s="Recording started!";
	            	       Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
	            	       t.show();
	            	       System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
	            	       LayoutParams layoutpars = window.getAttributes();
	            	       layoutpars.screenBrightness = brightness;
	            	       window.setAttributes(layoutpars);
	            	          mediaRecorder.start();
	            	 }
	            	 else
	            	 {
	            	 mediaRecorder.stop();
	            	 mediaRecorder.release();
	            	 videoing=false;
	                 System.putInt(cResolver, System.SCREEN_BRIGHTNESS, curBrightnessValue);
	                 LayoutParams layoutpars = window.getAttributes();
	                 layoutpars.screenBrightness = curBrightnessValue;
	                 window.setAttributes(layoutpars);
	                 String s="Recording captured and saved!";
	                 Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
	                 t.show();
	                 Intent myIntent = new Intent(c,Fourth.class);
	          		startActivity(myIntent);
	            	 }
	             }
	             return true;
	         }
			return true;
	     }
	 
	 
	 
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	 // TODO Auto-generated method stub

	}
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
	 // TODO Auto-generated method stub
	 prepareMediaRecorder();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	 // TODO Auto-generated method stub

	}
	private void initMediaRecorder(){
	       mediaRecorder.reset();

	 mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
	       mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
	       CamcorderProfile camcorderProfile_HQ = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
	       mediaRecorder.setProfile(camcorderProfile_HQ);
	       mediaRecorder.setOutputFile("/sdcard/DCIM/Camera/myvideo"+new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date())+".mp4");
	       
	      // mediaRecorder.setMaxDuration(60000); // Set max duration 60 sec.
	       //mediaRecorder.setMaxFileSize(5000000); // Set max file size 5M
	}

	private void prepareMediaRecorder(){
	 mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
	 try {
	  mediaRecorder.prepare();
	 } catch (IllegalStateException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 } catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
	}
}

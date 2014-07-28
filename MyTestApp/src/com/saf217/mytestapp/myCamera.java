package com.saf217.mytestapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Settings.System;

public class myCamera extends ActionBarActivity implements SurfaceHolder.Callback {

	//a variable to store a reference to the Image View at the main.xml file
    private ImageView iv_image;
    
    private VideoView vv_video;
    //a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;

    //a bitmap to display the captured image
    private Bitmap bmp;

    //Camera variables
    //a surface holder
    private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;
    ContentResolver cr ;
    Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		 View view = this.getWindow().getDecorView();
		    view.setBackgroundColor(Color.BLACK);
		    
		    context = getBaseContext();
		    cr = getContentResolver();

		    int brightness = 0;
    		//Content resolver used as a handle to the system's settings
    		ContentResolver cResolver;
    		//Window object, that will store a reference to the current window
    		Window window;
    		//Get the content resolver
    		cResolver = getContentResolver();

    		//Get the current window
    		window = getWindow();

    		// To handle the auto
    		Settings.System.putInt(cResolver,
    		Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    		//Get the current system brightness
    		brightness =0;            
    		         
		    //Set the system brightness using the brightness variable value
            System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
            //Get the current window attributes
            LayoutParams layoutpars = window.getAttributes();
            //Set the brightness of this window
            layoutpars.screenBrightness = brightness / (float)255;
            //Apply attribute changes to this window
            window.setAttributes(layoutpars);
		    
        //get the Image View at the main.xml file
        iv_image = (ImageView) findViewById(R.id.imageView);

        //get the Surface View at the main.xml file
        sv = (SurfaceView) findViewById(R.id.surfaceView);

        //Get a surface
        sHolder = sv.getHolder();

        //add the callback interface methods defined below as the Surface View callbacks
        sHolder.addCallback(this);

        //tells Android that this surface will have its data constantly replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		int brightness = 255;
		//Content resolver used as a handle to the system's settings
		ContentResolver cResolver;
		//Window object, that will store a reference to the current window
		Window window;
		//Get the content resolver
		cResolver = getContentResolver();

		//Get the current window
		window = getWindow();

		// To handle the auto
		Settings.System.putInt(cResolver,
		Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		//Get the current system brightness
		            
		         
	    //Set the system brightness using the brightness variable value
        System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
        //Get the current window attributes
        LayoutParams layoutpars = window.getAttributes();
        //Set the brightness of this window
        layoutpars.screenBrightness = 255;
        //Apply attribute changes to this window
        window.setAttributes(layoutpars);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

        // The Surface has been created, acquire the camera and tell it where
        // to draw the preview.
        mCamera = Camera.open();
        try {
           mCamera.setPreviewDisplay(holder);

        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

        //get camera parameters
        parameters = mCamera.getParameters();

        //set camera parameters
        mCamera.setParameters(parameters);
        mCamera.startPreview();

        //sets what code should be executed after the picture is taken
        Camera.PictureCallback mCall = new Camera.PictureCallback()
        {
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                //decode the data obtained by the camera into a Bitmap
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                TextView tv = (TextView)findViewById(R.id.textViewCamera);
                savePicture(bmp,"wowitworked");
                tv.setTextColor(Color.RED);
                tv.setText("WORKED");
                //set the iv_image
                iv_image.setImageBitmap(bmp);
            }
        };

        mCamera.takePicture(null, null, mCall);
	}
	private void savePicture(Bitmap bm, String imgName)
	 {  
	  OutputStream fOut = null;
	  String strDirectory = Environment.getExternalStorageDirectory().toString();

	  File f = new File(strDirectory, imgName);
	  try {
	   fOut = new FileOutputStream(f);
	   
	   /**Compress image**/
	   bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
	   fOut.flush();
	   fOut.close();

	   /**Update image to gallery**/
	   MediaStore.Images.Media.insertImage(getContentResolver(),
	    f.getAbsolutePath(), f.getName(), f.getName());
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		 //stop the preview
        mCamera.stopPreview();
        //release the camera
        mCamera.release();
        //unbind the camera from this object
        mCamera = null;
	}
	String mCurrentPhotoPath;

}

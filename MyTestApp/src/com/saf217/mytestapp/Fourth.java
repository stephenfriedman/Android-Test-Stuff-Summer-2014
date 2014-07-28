package com.saf217.mytestapp;

import java.io.IOException;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v4.app.Fragment;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.VideoView;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Settings.System;
import android.provider.Settings.SettingNotFoundException;

public class Fourth extends ActionBarActivity {
	MediaPlayer mp;
	SeekBar s;
	SeekBar s2;
	Context c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fourth);
		 View view = this.getWindow().getDecorView();
		    view.setBackgroundColor(Color.BLACK);
		mp=MediaPlayer.create(Fourth.this, R.raw.sail);
        mp.start();
        s2=(SeekBar)findViewById(R.id.seekBar2);
        s2.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
        	int progress =0;
        	public void onProgressChanged(SeekBar seekBar,int progressValue,boolean fromUser)
        	{
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
        		brightness = (int) (progressValue*2.5);
        		            
        		         
    		    //Set the system brightness using the brightness variable value
                System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
                //Get the current window attributes
                LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                layoutpars.screenBrightness = brightness / (float)255;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);
        	}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        });
		 s = (SeekBar)findViewById(R.id.seekBar1);
		  c = this;
		s.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		{
			int progress=0;
			int length=0;
			double progressDouble=0;
		    @Override
		      public void onProgressChanged(SeekBar seekBar,int progresValue, boolean fromUser)
		    	{
		    		double h = 0;
		    		progressDouble = (double)progresValue;
		    		h = progressDouble*mp.getDuration()/100;
		    		progressDouble=progressDouble/100;
		    		length = mp.getDuration();
		    		int into =(int) (length*progressDouble);
		    		mp.seekTo(into);
		    		String toToast = Double.toString(h);
		    		Toast t = Toast.makeText(c, toToast, 2);
		    		t.show();
		    	}
		    @Override
		      public void onStartTrackingTouch(SeekBar seekBar)
		    	{
		        // Do something here, 
		        //if you want to do anything at the start of
		        // touching the seekbar
		    	}

		      @Override
		      public void onStopTrackingTouch(SeekBar seekBar) 
		      {
		        // Display the value in textview
		        
		      }
		});
	}
	@Override
	protected void onRestart()
	{
		super.onStart();
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.seekTo(0);
		mp.start();
	}
	@Override
	protected void onStop()
	{
		super.onPause();
		mp.stop();
		//mp.release();
	}
	public void play(View view)
	{
		mp.stop();
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.seekTo(0);
		mp.start();
		s.setProgress(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.searchbarstuff, menu);
		    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.action_search)
		{
			return true;
		}
		else if(item.getItemId()==R.id.icon)
		{
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void searchBar(View view)
	{
		EditText et = (EditText)findViewById(R.id.editTextSearchBar);
		String goTo = "http://reddit.com/r/"+et.getText().toString();
		Uri webpage = Uri.parse(goTo);
		Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);
		Intent chooser = Intent.createChooser(webIntent, "Which browser??");
		PackageManager packageManager = getPackageManager();
		List activities = packageManager.queryIntentActivities(webIntent, 0);
		boolean isIntentSafe = activities.size() > 0;
		
		if (isIntentSafe) {
		    startActivity(chooser);
	}
		//startActivity(webIntent);
		
	}
	public void toCameraActivity(View view)
	{
		Button b = (Button)findViewById(R.id.toCameraActivity);
		Intent myIntent = new Intent(this,myCamera.class);
		startActivity(myIntent);
	}
	public void toVideoActivity(View view)
	{
		Button b = (Button)findViewById(R.id.toVideoActivity);
		Intent myIntent = new Intent(this,VideoActivity.class);
		startActivity(myIntent);
	}
	public void regularvideo(View view)
	{
		String s="Tap screen to record. Tap again to stop.";
	    Toast t = Toast.makeText(c, s, Toast.LENGTH_LONG);
	    t.show();
		Button b = (Button)findViewById(R.id.regularVideo);
		Intent myIntent = new Intent(this,RegularvideoActivity.class);
		startActivity(myIntent);
	}
}
	

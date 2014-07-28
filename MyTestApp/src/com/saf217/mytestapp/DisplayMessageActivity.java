package com.saf217.mytestapp;

import java.util.Date;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.os.Build;

public class DisplayMessageActivity extends ActionBarActivity {

	TextView c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		Intent intent =getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(message);
		
		
		
		setContentView(R.layout.activity_display_message);
		TimePicker p = (TimePicker)findViewById(R.id.timePicker1);
		p.setEnabled(true);
		p.setCurrentHour(12);
		p.setCurrentMinute(0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.searchbarstuff, menu);
		    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		int id = item.getItemId();
		if (id == R.id.action_search) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void goSmoke(View view)
	{
		Button smoke = (Button)findViewById(R.id.smokebutton);
		smoke.performHapticFeedback( HapticFeedbackConstants.LONG_PRESS);
		TimePicker tp = (TimePicker)findViewById(R.id.timePicker1);
		 c = (TextView)findViewById(R.id.celly);
		int hour = tp.getCurrentHour();
		int minute =tp.getCurrentMinute();
		
		 if((hour==4 &&minute==20) || (hour==16 &&minute==20))
		 {
			 c.setText("SMERKKKK");
			 c.setTextColor(Color.GREEN);
			 long d =System.currentTimeMillis();
			 Thread thread = new Thread()
			 {
				 public void run()
				 {
				 try {
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 finally
				 {
					 c.setTextColor(Color.MAGENTA);
				 }
				 }
			 };
			 
		 }
		 else
		 {
			 c.setText("Not 420");
			 c.setTextColor(Color.RED);
		 }
		 
		 
	}
	
	public void goInternet(View view)
	{
		EditText url = (EditText)findViewById(R.id.enterurl);
		String theUrl=url.getText().toString();
		if(theUrl.length()==0 || theUrl.equals(null))
		{
			theUrl=" ";
		}
		theUrl.trim();
		
		String mod=null;
		if(!theUrl.contains("http://")||!theUrl.contains("https://"))
		{
			if(theUrl.contains("www."))
				mod = "http://"+theUrl;
			else if(!theUrl.contains(".com")&&theUrl.length()>1)			
				mod = "http://www."+theUrl+".com";
			else
				mod = "http://www.google.com";
			
			mod.trim();
		}
		Uri webpage;
		if(!mod.equals(null))
		{
			 webpage = Uri.parse(mod);
		}
		else
		{
			webpage = Uri.parse(theUrl);
		}
		Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
		Intent chooser = Intent.createChooser(webIntent, "Which browser??");
		
		PackageManager packageManager = getPackageManager();
		List activities = packageManager.queryIntentActivities(webIntent, 0);
		boolean isIntentSafe = activities.size() > 0;
		
		if (isIntentSafe) {
		    startActivity(chooser);
	}
	
	}
	
	public void thirdActivity(View view)
	{
		Button b = (Button)findViewById(R.id.thirdActivity);
		b.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
		Intent myIntent = new Intent(this,SaveStuff.class);
		startActivity(myIntent);
	}
	

}

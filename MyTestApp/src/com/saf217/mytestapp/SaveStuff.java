package com.saf217.mytestapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore;

public class SaveStuff extends ActionBarActivity {
	private Context context;
	String filename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_stuff);
		context=this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.searchbarstuff, menu);
		    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		 int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
		*/
		
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

	public void saveStuff(View view)
	{
		EditText t= (EditText)findViewById(R.id.toSave);
		String s = t.getText().toString();

	 filename = "myfile123";
		FileOutputStream outputStream;

		try {
		  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		  outputStream.write(s.getBytes());
		  outputStream.close();
		} catch (Exception e) {
			t.setText("Problem!");
			t.setTextColor(Color.RED);
		  e.printStackTrace();
		}
		t.setTextColor(Color.BLUE);
		
		
		writeToSD(filename);
		t.setText("Wrote to SD");
		
	}
	public void getFile()
	{
		EditText t = (EditText)findViewById(R.id.toSave);
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+filename);
		FileInputStream in = null;
		try {
			in = openFileInput(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 InputStreamReader inputStreamReader = new InputStreamReader(in);
		    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    try {
				while ((line = bufferedReader.readLine()) != null) {
				    sb.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    t.setText(line);
	}
	
	@SuppressLint("NewApi") public File writeToSD(String filename)
	{
		File  file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),filename);
		  if (!file.mkdirs()) {
		        Log.e("ggg", "Directory not created");
		    }
		return file;
	}
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        ImageView iv = (ImageView)findViewById(R.id.imageView1);
	        iv.setImageBitmap(imageBitmap);
	    }
	}
	public void snapPic(View view)
	{
		dispatchTakePictureIntent();
	}
	public void toNextActivity(View view)
	{
		Button b = (Button)findViewById(R.id.nextActivity);
		b.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
		Intent i = new Intent(this,Fourth.class);
		startActivity(i);
	}
	
}

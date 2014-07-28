package com.saf217.mytestapp;

//import android.R;
import java.io.IOException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import android.os.Build;

public class MainActivity extends ActionBarActivity {

   MediaPlayer mp;
   public static final String EXTRA_MESSAGE = "com.saf217.mytestapp.MESSAGE";
   TextView t;
   EditText editTexter;
   int count=0;
   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     
        t = (TextView)findViewById(R.id.textView1);
        t.setText("Blank1");
        editTexter = (EditText) findViewById(R.id.editText1);
        mp=MediaPlayer.create(MainActivity.this, R.raw.sail);
        mp.start();
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

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.searchbarstuff, menu);
	    
	    return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
        	t.setText("Menu button worked!");
        	t.setTextColor(Color.RED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi") public void appear(View view)
    {
    	Intent intent =new Intent(this,Fourth.class);
    	PendingIntent startCam =  PendingIntent.getActivity(this, 0, intent,0);
    	Notification mBuilder =
    	        new Notification.Builder(this)
    	        .setSmallIcon(R.drawable.camera)
    	        .setContentTitle("My notification")
    	        .setContentText("Hello World!")
    	        .setContentIntent(startCam)
    	        .setAutoCancel(true)
    	        .setPriority(Notification.PRIORITY_MAX)
    	        .addAction(R.drawable.camera,"Record",startCam).build();
    	mBuilder.flags=Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
    
    	NotificationManager notificationManager =
    	    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	notificationManager.notify(0, mBuilder);
    	
    	
    	Button b3 = (Button)findViewById(R.id.button3);
    	b3.performHapticFeedback( HapticFeedbackConstants.LONG_PRESS);
    
    	if(editTexter.getText().toString().length()>=1)
    	{
    		t.setText(editTexter.getText().toString());
	    	t.setTextColor(Color.CYAN);
	    	
	    	SharedPreferences pref = this.getSharedPreferences("com.saf217.mytestapp", Context.MODE_PRIVATE);
	    	SharedPreferences.Editor editor = pref.edit();
	    	
	    	
	    	TextView t = (TextView)findViewById(R.id.textView1);
	    	String text = t.getText().toString();
	       	editor.putString("givenText", text);
	       	editor.commit();
    	}
       	else
    		t.setText("------");
       	
    }
    public void loadText(View view)
    {
    	Button b4 = (Button)findViewById(R.id.button4);
    	b4.performHapticFeedback( HapticFeedbackConstants.LONG_PRESS);
    	TextView t = (TextView)findViewById(R.id.textView1);
      	SharedPreferences pref = this.getSharedPreferences("com.saf217.mytestapp", Context.MODE_PRIVATE);
    	String s = pref.getString("givenText", "No text saved yet");
    	if(t.getText().toString().equals("cat"))
    		t.setText("Blank given");
    	else
    		t.setText(s);
    }
    public void sendMessage(View view) {
    	Button b2 = (Button)findViewById(R.id.button2);
    	b2.performHapticFeedback( HapticFeedbackConstants.LONG_PRESS);
        Intent myIntent = new Intent(this,DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        myIntent.putExtra(EXTRA_MESSAGE, message);
        startActivity(myIntent);
        }

}

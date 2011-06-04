package com.url2tv;

import com.url2tv.SSDP.SSDPTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	static final String TAG = "com.url2tv.MainActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "MainActivity()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SSDPTask task = new SSDPTask(this);
        task.execute();
    }
}
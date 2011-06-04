package com.url2tv.SSDP;

import android.content.Context;
import android.os.AsyncTask;

public class SSDPTask extends AsyncTask {
	private Context mContext;
	
	public SSDPTask(Context context){
		mContext = context;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		SSDP s = new SSDP(mContext);
		
		s.sendDiscover();
		s.receiver();

		return null;
	}

}

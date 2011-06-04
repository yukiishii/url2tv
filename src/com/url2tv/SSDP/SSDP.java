package com.url2tv.SSDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.util.Log;

public class SSDP {
	static Context mContext;
	public final static String TAG = "com.url2tv.SSDP";
	public final static String MULTICAST_ADDRESS = "239.255.255.250";
	public final static short DEFAULT_PORT = 1900;
	static final int BUFSIZE = 1024; 
	private final static String DISCOVER_MESSAGE =
	    "M-SEARCH * HTTP/1.1\r\n" +
	    "ST: upnp:rootdevice\r\n" +
	    "MX: 3\r\n" +
	    "MAN: \"ssdp:discover\"\r\n" +
	    "HOST: 239.255.255.250:1900\r\n\r\n";
	
	public SSDP(Context context) {
		mContext = context;
	}
	
	public static void sendDiscover() {
		Log.d(TAG, "sendDiscover()");
		try {
			MulticastSocket soc = new MulticastSocket(DEFAULT_PORT);
			byte[] buf = DISCOVER_MESSAGE.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(MULTICAST_ADDRESS), DEFAULT_PORT);
			soc.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static void receiver() {
		Log.d(TAG, "receiver()");
		try {
			WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
			MulticastLock lock = manager.createMulticastLock("lock name");
			lock.setReferenceCounted(true);
			lock.acquire();
			
			byte[] buf  = new byte[BUFSIZE];
			MulticastSocket soc = new MulticastSocket(DEFAULT_PORT);
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			soc.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));  
        
			while(true) {
				soc.receive(packet);
				String rec_str = new String(packet.getData());
				Log.d(TAG, rec_str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.tarena.tlbs.receiver;


import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.util.Const;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 此广播接收器是接受系统的网络是否开启以及开启类型
 * 
 * @author Administrator
 *
 */
public class NetWorkStateChangedReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		if(networkInfo==null){
			TApplication.network_type=Const.NETWORK_NONE;
			Log.i("NetworkStateChangedReceiver", "用户关网了");
		}else{
			NetworkInfo wifiInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(wifiInfo!=null && wifiInfo.isConnected()){
				TApplication.network_type=Const.NETWORK_TYPE_WIFI;
				Log.i("NetworkStateChangedReceiver", "用户连接的是无线");
			}
			NetworkInfo mobileInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if(mobileInfo!=null && mobileInfo.isConnected()){
				TApplication.network_type=Const.NETWORK_TYPE_MOBILE;
				Log.i("NetworkStateChangedReceiver", "用户连接的是流量");
			}
		}
	}
	
	
}

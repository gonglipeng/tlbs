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
 * �˹㲥�������ǽ���ϵͳ�������Ƿ����Լ���������
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
			Log.i("NetworkStateChangedReceiver", "�û�������");
		}else{
			NetworkInfo wifiInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(wifiInfo!=null && wifiInfo.isConnected()){
				TApplication.network_type=Const.NETWORK_TYPE_WIFI;
				Log.i("NetworkStateChangedReceiver", "�û����ӵ�������");
			}
			NetworkInfo mobileInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if(mobileInfo!=null && mobileInfo.isConnected()){
				TApplication.network_type=Const.NETWORK_TYPE_MOBILE;
				Log.i("NetworkStateChangedReceiver", "�û����ӵ�������");
			}
		}
	}
	
	
}

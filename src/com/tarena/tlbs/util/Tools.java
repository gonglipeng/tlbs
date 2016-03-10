package com.tarena.tlbs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

public class Tools {
	public static void writeToSdcard(String path,String fileName,byte [] data){
		//判断是否有sdcard卡
		String sdcard_state=Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(sdcard_state)){
			FileOutputStream fileOutputStream=null;
			try {
				//文件夹不存在则创建
				File fileDirectory=new File(path);
				if(!fileDirectory.exists()){
					fileDirectory.mkdirs();
				}
				File file=new File(fileDirectory,fileName);
				if(file.exists()){
					file.delete();
				}
				file.createNewFile();
				fileOutputStream=new FileOutputStream(file);
				fileOutputStream.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(fileOutputStream!=null){
					fileOutputStream.close();
					fileOutputStream=null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
	}
	
	
	private static ProgressDialog progressDialog;

	public static void closeProgressDialog() {
		progressDialog.cancel();
		progressDialog = null;
		System.gc();
	}

	public static void showProgressDialog(Activity activity, String msg) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(msg);
			// 在屏幕上单击dialog不会消失
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}
	}

	/**
	 * 得版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			versionName = packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
}

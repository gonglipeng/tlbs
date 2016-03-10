package com.tarena.tlbs.biz;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.entity.UpdateEntity;
import com.tarena.tlbs.parser.UpdateParser;
import com.tarena.tlbs.util.Const;
import com.tarena.tlbs.util.Tools;
import com.tarena.tlbs.view.SettingActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 更新版本操作： 利用asyncHttpClient异步联接网络
 * 
 * @author Administrator
 *
 */
public class UpdateBiz {
	public void getVersion(final Handler handler) {
		String url = "http://192.168.1.101:8080/tlbsServer/servlet/ApkUpdateServlet";
		TApplication.asyncHttpClient.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String jsonString = new String(responseBody);
				UpdateEntity updateEntity = new UpdateParser().parser(jsonString.substring(2));
				Message message = handler.obtainMessage();
				message.what = SettingActivity.MSG_SHOW_VERSION;
				Bundle bundle = new Bundle();
				bundle.putSerializable(Const.KEY_DATA, updateEntity);
				message.setData(bundle);
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void getApk(final Handler handler, final String apkUrl) {
		TApplication.asyncHttpClient.get(apkUrl, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				String fileName=null;
				try {
					// apkUrl="http://192.168.188.98:8080/tlbsServer/zhangjiujunTlbs.apk"
					int index = apkUrl.lastIndexOf("/");
					fileName = apkUrl.substring(index + 1);
					Tools.writeToSdcard(Const.DWONLOAD_PATH, fileName, responseBody);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					String filePath=Const.DWONLOAD_PATH+"/"+fileName;
					Message message=handler.obtainMessage();
					message.what=SettingActivity.MSG_INSTALL_APK;
					Bundle bundle=new Bundle();
					bundle.putString(Const.KEY_DATA, filePath);
					message.setData(bundle);
					handler.sendMessage(message);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub

			}
		});
	}
}

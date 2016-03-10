package com.tarena.tlbs.biz;

import java.util.HashMap;

import org.jivesoftware.smack.AccountManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.entity.UserEntity;
import com.tarena.tlbs.util.Const;

public class RegisterBiz {
	public void register(final Handler handler,final UserEntity userEntity) {
		new Thread() {
			public void run() {
				int status=Const.STATUS_OK;
				try {

					// 帐户管理
					AccountManager accountManager = TApplication.xmppConnection
							.getAccountManager();
					// 呢称必须放到map中
					HashMap<String, String> map = new HashMap<String, String>();
					// 放的是呢称，key必须是name map.get("name")
					map.put("name", userEntity.getName());
					accountManager.createAccount(userEntity.getUsername(),
							userEntity.getPassword(), map);
				} catch (Exception e) {
					
					//处理错误，如果有错误，设置状态码
					String string=e.toString();
					if ("conflict(409)".equals(string)){
						status=2;
     				}else if(!"".equals(string)){
     					status=3;
     				}
					Log.i("register", string);
				}finally
				{
					//发消息
					//1,得到message
					Message message=handler.obtainMessage();
					//2,传数据
					//用bunlde比msg.obj好在传多个数据，推荐用bundle
					Bundle bundle=new Bundle();
					bundle.putInt(Const.KEY_DATA, status);
					message.setData(bundle);
					//3,发送
					handler.sendMessage(message);
					
				}
			};
		}.start();
	}
}

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

					// �ʻ�����
					AccountManager accountManager = TApplication.xmppConnection
							.getAccountManager();
					// �سƱ���ŵ�map��
					HashMap<String, String> map = new HashMap<String, String>();
					// �ŵ����سƣ�key������name map.get("name")
					map.put("name", userEntity.getName());
					accountManager.createAccount(userEntity.getUsername(),
							userEntity.getPassword(), map);
				} catch (Exception e) {
					
					//�����������д�������״̬��
					String string=e.toString();
					if ("conflict(409)".equals(string)){
						status=2;
     				}else if(!"".equals(string)){
     					status=3;
     				}
					Log.i("register", string);
				}finally
				{
					//����Ϣ
					//1,�õ�message
					Message message=handler.obtainMessage();
					//2,������
					//��bunlde��msg.obj���ڴ�������ݣ��Ƽ���bundle
					Bundle bundle=new Bundle();
					bundle.putInt(Const.KEY_DATA, status);
					message.setData(bundle);
					//3,����
					handler.sendMessage(message);
					
				}
			};
		}.start();
	}
}

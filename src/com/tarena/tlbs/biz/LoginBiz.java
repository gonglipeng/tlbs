package com.tarena.tlbs.biz;

import android.content.Intent;
import android.util.Log;

import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.entity.UserEntity;
import com.tarena.tlbs.util.Const;
//������1��android ui(Listview,button)�ǲ����̰߳�ȫ
//java������̰߳�ȫ�����̷߳����࣬����������̲߳���ȫ
//StringBuffer��ȫ,StringBuilder����ȫ
//ArrayList����ȫ,Vector��ȫ
//���ǣ�ListView �̲߳���ȫ���죬�����߳��в��ܷ���ListView
//�������
//1,handler����Ϣ
//2,���㲥
//3,runOnUiThread()

//������2������Ϣ�����㲥����
//�������ܽ���ϵͳ���Ĺ㲥�����Ӧ�÷��Ĺ㲥������Ϣֻ�ܱ�Ӧ���е���Ϣ

public class LoginBiz {

	public void login(final UserEntity userEntity) {
		new Thread("loginThread") {
			public void run() {
				// ״̬
				int status = -1;
				try {
					// this.sleep(10000);
					long loginTime = System.currentTimeMillis();
					Log.i("loginBiz", "ThreadId="
							+ Thread.currentThread().getId() + "loginTime="
							+ (loginTime - TApplication.appStartTime));
					// ��Ҫ�����̵߳���Դ
					// int count=0;
					// while(count<1000&&TApplication.xmppConnection
					// .isConnected()==false)
					// {
					// this.sleep(100);
					// count++;
					// Log.i("loginBiz", "count="+count);
					//
					// }
					if (TApplication.xmppConnection.isConnected() == false) {
						Log.i("loginBiz", "start wait");
						synchronized (TApplication.instance) {
							TApplication.instance.wait();
						}
						Log.i("loginBiz", "stop wait");
					}

					// �ж��Ƿ������Ϸ�����
					if (TApplication.xmppConnection.isConnected() == false) {
						status = Const.STATUS_CONNECT_FAILURE;
					} else {
						TApplication.xmppConnection.login(
								userEntity.getUsername(),
								userEntity.getPassword());
						boolean isLoginSuccess = TApplication.xmppConnection
								.isAuthenticated();
						if (isLoginSuccess) {
							status = Const.STATUS_OK;
							TApplication.currentUser=userEntity.getUsername()+"@"+TApplication.serviceName;
						}

						Log.i("loginBiz", "��¼���=" + isLoginSuccess);
					}
				}catch(NullPointerException e2)
				{
					
				}
				catch (Exception e) {
					//�ж��쳣�����ַ�ʽ
					//1,�Ӷ��catch,��ͬ���쳣ִ�е�catcnҲ��һ��
					//2,��instance of
					//3,e.toString(),��ͬ���쳣���ص��ַ�����һ��
					// ��¼ʧ��
					String eInfo=e.toString();
					if ("java.lang.IllegalStateException: Already logged in to server.".equals(eInfo))
					{
						
					}
					if ("SASL authentication failed using mechanism DIGEST-MD5: ".equals(eInfo))
					{
						Log.i("loginBiz", "�������");
						//�Ͽ����ӣ�������������ȷ������ܵ�¼�ɹ�
						//��samackȱ�㡣
						TApplication.xmppConnection.disconnect();
						TApplication.instance.connectChatServer();
					}
					if (e instanceof NullPointerException)
					{
						
					}
					status = Const.STATUS_LOGIN_FAILURE;
					e.printStackTrace();
				} finally {
					// ���㲥
					Intent intent = new Intent(Const.ACTION_LOGIN);
					//������
					intent.putExtra(Const.KEY_DATA, status);
					TApplication.instance.sendBroadcast(intent);
				}
			};
		}.start();

	}
}

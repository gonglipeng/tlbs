package com.tarena.tlbs.biz;

import android.content.Intent;
import android.util.Log;

import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.entity.UserEntity;
import com.tarena.tlbs.util.Const;
//面试题1：android ui(Listview,button)是不是线程安全
//java中类分线程安全（多线程访问类，不会出错）和线程不安全
//StringBuffer安全,StringBuilder不安全
//ArrayList不安全,Vector安全
//答案是：ListView 线程不安全，快，工作线程中不能访问ListView
//解决方法
//1,handler发消息
//2,发广播
//3,runOnUiThread()

//面试题2：发消息，发广播区别
//接收器能接收系统发的广播，别的应用发的广播，收消息只能本应用中的消息

public class LoginBiz {

	public void login(final UserEntity userEntity) {
		new Thread("loginThread") {
			public void run() {
				// 状态
				int status = -1;
				try {
					// this.sleep(10000);
					long loginTime = System.currentTimeMillis();
					Log.i("loginBiz", "ThreadId="
							+ Thread.currentThread().getId() + "loginTime="
							+ (loginTime - TApplication.appStartTime));
					// 需要连接线程的资源
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

					// 判断是否连接上服务器
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

						Log.i("loginBiz", "登录结果=" + isLoginSuccess);
					}
				}catch(NullPointerException e2)
				{
					
				}
				catch (Exception e) {
					//判断异常有三种方式
					//1,加多个catch,不同的异常执行的catcn也不一样
					//2,用instance of
					//3,e.toString(),不同的异常返回的字符串不一样
					// 登录失败
					String eInfo=e.toString();
					if ("java.lang.IllegalStateException: Already logged in to server.".equals(eInfo))
					{
						
					}
					if ("SASL authentication failed using mechanism DIGEST-MD5: ".equals(eInfo))
					{
						Log.i("loginBiz", "密码错误");
						//断开连接，重连，输入正确密码才能登录成功
						//是samack缺点。
						TApplication.xmppConnection.disconnect();
						TApplication.instance.connectChatServer();
					}
					if (e instanceof NullPointerException)
					{
						
					}
					status = Const.STATUS_LOGIN_FAILURE;
					e.printStackTrace();
				} finally {
					// 发广播
					Intent intent = new Intent(Const.ACTION_LOGIN);
					//带数据
					intent.putExtra(Const.KEY_DATA, status);
					TApplication.instance.sendBroadcast(intent);
				}
			};
		}.start();

	}
}

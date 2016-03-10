package com.tarena.tlbs;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.packet.RosterPacket.Item;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.loopj.android.http.AsyncHttpClient;
import com.tarena.tlbs.entity.PrivateChatEntity;
import com.tarena.tlbs.util.Const;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.telephony.gsm.SmsMessage.MessageClass;
import android.util.Log;

//Application应用，TApplication对象生命周期很长，从项目启动开始，直到结束掉进程
//程序一启动要做的事(创建连接)放到TApplication
//全局变量
//TApplication必须在manifest.xml中注册
public class TApplication extends Application {
	// release 用戶可以正常使用
	public static final boolean isRelease = false;
	public static XMPPConnection xmppConnection;
	public static String host, serviceName;
	int port;
	public static int network_type = Const.NETWORK_TYPE_WIFI;
	public static long appStartTime;

	public static TApplication instance;

	public static ArrayList<Activity> listActivity = new ArrayList();
	// 做全局，减少对象的个数
	public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	/**
	 * 是添加好友的结果 是添加内容
	 */
	public static ArrayList<Object> listMsg = new ArrayList<Object>();
	public static String currentUser;
	public static String name;
	public static MultiUserChat groupChat;

	public void exit() {
		// 关闭所有activity
		for (Activity activity : listActivity) {
			activity.finish();
		}
		// 断开与服务器的连接
		xmppConnection.disconnect();
		// 结束进程
		System.exit(0);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		appStartTime = System.currentTimeMillis();
		Log.i("loginBiz", "run app.onCreate(),ThreadId=" + Thread.currentThread().getId());
		// 有真机，每天带过来，代码在真机上测试
		// 在真机的浏览器中输入http://ip:9090,出现后台管理界面，说明真机能连上聊天服务器，不出现，网出问题
		readConfig();
		connectChatServer();
	}

	private void readConfig() {

		try {
			Resources resources = this.getResources();
			// 访问的是res/xml/config.xml
			// xmlResourceParser的父类是XmlPullParser
			XmlResourceParser xmlResourceParser = resources.getXml(R.xml.config);

			// 流中的内容
			// <config><host>124.207.192.18</host><port>5222</port><serviceName>tarena.com</serviceName></config>
			// 流中有个指针 |
			// getEventType
			int eventType = xmlResourceParser.getEventType();

			while (eventType != XmlResourceParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlResourceParser.START_TAG:
					String tagName = xmlResourceParser.getName();
					if ("host".equals(tagName)) {
						host = xmlResourceParser.nextText();
					}
					if ("serviceName".equals(tagName)) {
						serviceName = xmlResourceParser.nextText();
					}
					if ("port".equals(tagName)) {
						port = Integer.parseInt(xmlResourceParser.nextText());
					}
					break;
				case XmlResourceParser.END_TAG:

					break;

				default:
					break;
				}
				eventType = xmlResourceParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectChatServer() {
		ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port, serviceName);
		// 推荐用证书加密，在wireshark中看不到xmpp数据。
		// connectionConfiguration
		// .setSecurityMode(SecurityMode.required);
		// 不用asmack加密
		connectionConfiguration.setSecurityMode(SecurityMode.disabled);
		// 1，应用层，对数据进行加密
		// 2,传输层加密，用证书
		// 3,网络层加密
		// 4,硬件层加密

		xmppConnection = new XMPPConnection(connectionConfiguration);

		registerListenerInterceptor();
		new Thread("ConnectChatServerThread") {
			public void run() {
				try {
					// this.sleep(30000);
					// 本地班用172.60.5.100

					xmppConnection.connect();

					long connectEndTime = System.currentTimeMillis();
					Log.i("loginBiz", "ThreadId=" + Thread.currentThread().getId() + "connectEndTime="
							+ (connectEndTime - appStartTime));
					Log.i("connectChatServer", "连接结果=" + xmppConnection.isConnected());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					synchronized (TApplication.instance) {
						TApplication.instance.notify();
						Log.i("loginBiz", "notify");

					}
				}
			};
		}.start();
	}

	private void registerListenerInterceptor() {
		// TODO Auto-generated method stub
		// null表示不管任何情况都返回值
		xmppConnection.addPacketInterceptor(new AllPacketInterceptor(), null);
		xmppConnection.addPacketListener(new AllPacketListener(), null);
	}

	/**
	 * 拦截发出去的信息(可以看见对象和内容)
	 * 
	 * @author Administrator
	 *
	 */
	class AllPacketInterceptor implements PacketInterceptor {

		@Override
		public void interceptPacket(Packet packet) {
			// TODO Auto-generated method stub
			String objectInfo = packet.toString();
			String xml = packet.toXML();
			Log.i("AllPacketInterceptor", "对象:" + objectInfo + "\n内容xml：" + xml);
		}

	}

	/**
	 * 监听返回得到的对象及内容(xml格式)
	 */
	class AllPacketListener implements PacketListener {

		@Override
		public void processPacket(Packet packet) {
			// TODO Auto-generated method stub
			String objectInfo = packet.toString();
			String xml = packet.toXML();
			Log.i("AllPacketListener", "对象：" + objectInfo + "\n内容xml：" + xml);

			/***
			 * packet的实际类型对应xml中标签名
			 * <presence id="DCfyk-34" to="zhangjiujun@tarena.com" from=
			 * "a1@tarena.com" type="unsubscribe"> </presence>
			 * 
			 */
			if (packet instanceof Presence) {
				Presence presence = ((Presence) packet);
				Type type = presence.getType();
				String userName = presence.getFrom();
				if (type == Type.unsubscribe) {
					Log.i("添加好友", userName + "不同意");
					updateMyMsg(presence);
				}
			}

			if (packet instanceof RosterPacket) {
				RosterPacket rosterPacket = (RosterPacket) packet;
				// ArrayList可以放集合，将集合转换为list
				List<Item> lists = new ArrayList(rosterPacket.getRosterItems());
				for (Item list : lists) {
					if (list.getItemType() == ItemType.both) {
						Log.i("添加好友", list.getUser() + "同意");
						updateMyMsg(list.getUser() + "同意");
					}
				}
			}

			if (packet instanceof Message) {
				Message message = (Message) packet;
				Message.Type type = message.getType();
				if (type == Message.Type.chat) {
					String fromFriend = message.getFrom();
					if (fromFriend.contains("/")) {
						fromFriend = fromFriend.substring(0, fromFriend.indexOf("/"));
					}
					PrivateChatEntity.addMessage(fromFriend, message);
					Intent intent = new Intent(Const.ACTION_SEND_PRIVATE_CHAT_MSG);
					TApplication.instance.sendBroadcast(intent);
				}
			}
		}

	}

	public void updateMyMsg(Object object) {
		listMsg.add(object);
		Intent intent = new Intent(Const.UPDATE_MY_MSG);
		sendBroadcast(intent);
	}
}

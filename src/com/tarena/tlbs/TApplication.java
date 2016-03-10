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

//ApplicationӦ�ã�TApplication�����������ںܳ�������Ŀ������ʼ��ֱ������������
//����һ����Ҫ������(��������)�ŵ�TApplication
//ȫ�ֱ���
//TApplication������manifest.xml��ע��
public class TApplication extends Application {
	// release �Ñ���������ʹ��
	public static final boolean isRelease = false;
	public static XMPPConnection xmppConnection;
	public static String host, serviceName;
	int port;
	public static int network_type = Const.NETWORK_TYPE_WIFI;
	public static long appStartTime;

	public static TApplication instance;

	public static ArrayList<Activity> listActivity = new ArrayList();
	// ��ȫ�֣����ٶ���ĸ���
	public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	/**
	 * ����Ӻ��ѵĽ�� ���������
	 */
	public static ArrayList<Object> listMsg = new ArrayList<Object>();
	public static String currentUser;
	public static String name;
	public static MultiUserChat groupChat;

	public void exit() {
		// �ر�����activity
		for (Activity activity : listActivity) {
			activity.finish();
		}
		// �Ͽ��������������
		xmppConnection.disconnect();
		// ��������
		System.exit(0);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		appStartTime = System.currentTimeMillis();
		Log.i("loginBiz", "run app.onCreate(),ThreadId=" + Thread.currentThread().getId());
		// �������ÿ�������������������ϲ���
		// ������������������http://ip:9090,���ֺ�̨������棬˵���������������������������֣���������
		readConfig();
		connectChatServer();
	}

	private void readConfig() {

		try {
			Resources resources = this.getResources();
			// ���ʵ���res/xml/config.xml
			// xmlResourceParser�ĸ�����XmlPullParser
			XmlResourceParser xmlResourceParser = resources.getXml(R.xml.config);

			// ���е�����
			// <config><host>124.207.192.18</host><port>5222</port><serviceName>tarena.com</serviceName></config>
			// �����и�ָ�� |
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
		// �Ƽ���֤����ܣ���wireshark�п�����xmpp���ݡ�
		// connectionConfiguration
		// .setSecurityMode(SecurityMode.required);
		// ����asmack����
		connectionConfiguration.setSecurityMode(SecurityMode.disabled);
		// 1��Ӧ�ò㣬�����ݽ��м���
		// 2,�������ܣ���֤��
		// 3,��������
		// 4,Ӳ�������

		xmppConnection = new XMPPConnection(connectionConfiguration);

		registerListenerInterceptor();
		new Thread("ConnectChatServerThread") {
			public void run() {
				try {
					// this.sleep(30000);
					// ���ذ���172.60.5.100

					xmppConnection.connect();

					long connectEndTime = System.currentTimeMillis();
					Log.i("loginBiz", "ThreadId=" + Thread.currentThread().getId() + "connectEndTime="
							+ (connectEndTime - appStartTime));
					Log.i("connectChatServer", "���ӽ��=" + xmppConnection.isConnected());
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
		// null��ʾ�����κ����������ֵ
		xmppConnection.addPacketInterceptor(new AllPacketInterceptor(), null);
		xmppConnection.addPacketListener(new AllPacketListener(), null);
	}

	/**
	 * ���ط���ȥ����Ϣ(���Կ������������)
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
			Log.i("AllPacketInterceptor", "����:" + objectInfo + "\n����xml��" + xml);
		}

	}

	/**
	 * �������صõ��Ķ�������(xml��ʽ)
	 */
	class AllPacketListener implements PacketListener {

		@Override
		public void processPacket(Packet packet) {
			// TODO Auto-generated method stub
			String objectInfo = packet.toString();
			String xml = packet.toXML();
			Log.i("AllPacketListener", "����" + objectInfo + "\n����xml��" + xml);

			/***
			 * packet��ʵ�����Ͷ�Ӧxml�б�ǩ��
			 * <presence id="DCfyk-34" to="zhangjiujun@tarena.com" from=
			 * "a1@tarena.com" type="unsubscribe"> </presence>
			 * 
			 */
			if (packet instanceof Presence) {
				Presence presence = ((Presence) packet);
				Type type = presence.getType();
				String userName = presence.getFrom();
				if (type == Type.unsubscribe) {
					Log.i("��Ӻ���", userName + "��ͬ��");
					updateMyMsg(presence);
				}
			}

			if (packet instanceof RosterPacket) {
				RosterPacket rosterPacket = (RosterPacket) packet;
				// ArrayList���Էż��ϣ�������ת��Ϊlist
				List<Item> lists = new ArrayList(rosterPacket.getRosterItems());
				for (Item list : lists) {
					if (list.getItemType() == ItemType.both) {
						Log.i("��Ӻ���", list.getUser() + "ͬ��");
						updateMyMsg(list.getUser() + "ͬ��");
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

package com.tarena.tlbs.entity;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.jivesoftware.smack.packet.Message;

public class PrivateChatEntity {
	/**
	 * �洢������Ϣ
	 * ��һ���������û���
	 * �ڶ��������������¼
	 */
	public static ConcurrentHashMap<String, Vector<Message>> map = 
			new ConcurrentHashMap<String, Vector<Message>>();

	/**
	 * 
	 * @param friendName    
	 * @param message
	 * if�ж�������û��������ѵ�����
	 * ���û�У�����������ѵ�Map
	 * �����ֱ�����������Ϣ
	 */
	public static void addMessage(String friendName,Message message) {
		Vector<Message> messages=PrivateChatEntity.map.get(friendName);
		if(messages==null){
			messages=new Vector<Message>();
			PrivateChatEntity.map.put(friendName, messages);
		}
		messages.add(message);
	}
}

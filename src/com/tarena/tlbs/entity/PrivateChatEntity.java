package com.tarena.tlbs.entity;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.jivesoftware.smack.packet.Message;

public class PrivateChatEntity {
	/**
	 * 存储聊天信息
	 * 第一个参数：用户名
	 * 第二个参数：聊天记录
	 */
	public static ConcurrentHashMap<String, Vector<Message>> map = 
			new ConcurrentHashMap<String, Vector<Message>>();

	/**
	 * 
	 * @param friendName    
	 * @param message
	 * if判断上来有没有这个朋友的名称
	 * 如果没有，创建这个朋友的Map
	 * 如果有直接添加聊天信息
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

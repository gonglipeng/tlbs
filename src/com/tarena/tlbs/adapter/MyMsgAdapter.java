package com.tarena.tlbs.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.tarena.tlbs.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyMsgAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Object> listMsg;

	public MyMsgAdapter(Context context, ArrayList<Object> listMsg) {
		// TODO Auto-generated constructor stub
		this.context = context;
		if (listMsg != null) {
			this.listMsg = listMsg;
		} else {
			this.listMsg = new ArrayList<Object>();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMsg.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listMsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder=new ViewHolder();
			convertView=View.inflate(context, R.layout.my_msg_item, null);
			viewHolder.llAddFriendResult = (LinearLayout) convertView
					.findViewById(R.id.ll_my_msg_item_addFriendResult);
			viewHolder.llChatContent = (LinearLayout) convertView
					.findViewById(R.id.ll_my_msg_item_chat);
			viewHolder.tvAddFreindResult = (TextView) convertView
					.findViewById(R.id.tv_my_msg_item_addFriendResult);
			viewHolder.tvChatContent = (TextView) convertView
					.findViewById(R.id.tv_my_msg_item_chatContent);
			convertView.setTag(viewHolder);
		} else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		Object data=listMsg.get(position);
		if(data instanceof Presence){
			Presence presence=(Presence)data;
			String user=presence.getFrom();
			viewHolder.llAddFriendResult.setVisibility(View.VISIBLE);
			viewHolder.tvAddFreindResult.setText(user.subSequence(0, user.indexOf("@"))+"不同意请求");
		}else if(data instanceof String){
			viewHolder.llAddFriendResult.setVisibility(View.VISIBLE);
			String userName=String.valueOf(data);
			viewHolder.tvAddFreindResult.setText(userName.subSequence(0, userName.indexOf("@"))+"同意请求");
		}else if(data instanceof Message){
			viewHolder.llChatContent.setVisibility(View.VISIBLE);
			viewHolder.tvChatContent.setText(((Message)data).getBody());
		}
		return convertView;
	}

	/**
	 * 当有新的消息时刷新通知界面刷新
	 * @author Administrator
	 *
	 */
	public void updateDate(ArrayList<Object> listMsg){
		this.listMsg=listMsg;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder {
		LinearLayout llAddFriendResult, llChatContent;
		TextView tvAddFreindResult, tvChatContent;
		Button btnChat;
	}
}

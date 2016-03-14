package com.tarena.tlbs.biz;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.dao.MessageDao;
import com.tarena.tlbs.entity.PrivateChatEntity;
import com.tarena.tlbs.util.Const;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class PrivateChatTask extends AsyncTask<String, Void, Void>{

	Context context;
	
	public PrivateChatTask(Context context) {
		this.context=context;
	}
	@Override
	protected Void doInBackground(String... params) {
		try {
			Log.i("PrivateChatBiz", "toUserName=" + params[0] + ",body="
					+ params[1]);
			Message message=new Message();
			message.setTo(params[0]);
			message.setBody(params[1]);
			message.setFrom(TApplication.currentUser);
			message.setType(Type.chat);
			PrivateChatEntity.addMessage(params[0], message);
			MessageDao messageDao=new MessageDao(context);
			messageDao.insert(message);
			//注意：此处可能由系统错误 所以在最后一句执行
			TApplication.xmppConnection.sendPacket(message);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}finally{
			Intent intent=new Intent(Const.ACTION_SEND_PRIVATE_CHAT_MSG);
			TApplication.instance.sendBroadcast(intent);
		}
		return null;
	}

}

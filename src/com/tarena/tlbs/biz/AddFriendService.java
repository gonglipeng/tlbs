package com.tarena.tlbs.biz;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPException;

import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.entity.FriendEntity;
import com.tarena.tlbs.util.Const;

import android.app.IntentService;
import android.content.Intent;

public class AddFriendService extends IntentService{

	public AddFriendService() {
		super("");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		try {
			FriendEntity friendEntity=(FriendEntity)intent.getSerializableExtra(Const.KEY_DATA);
			String user=friendEntity.getUserName()+"@"+TApplication.serviceName;
			String [] groups={friendEntity.getGroup()};
			Roster roster=TApplication.xmppConnection.getRoster();
			roster.createEntry(user, friendEntity.getName(),groups);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Intent intent2=new Intent(Const.ACTION_SEND_ADD_FRIEND);
			sendBroadcast(intent2);
		}
	}

}

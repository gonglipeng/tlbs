package com.tarena.tlbs.view;

import com.tarena.tlbs.R;
import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.adapter.MyMsgAdapter;
import com.tarena.tlbs.util.Const;
import com.tarena.tlbs.view.AddFriendActivity.MyReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MyMsgActivity extends BaseActivity {

	ImageView ivShowMenu;
	Menu menu;
	ListView lv;
	MyMsgAdapter msgAdapter;
	MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.my_msg);
			setupView();
			addListener();
			menu = new Menu(this);
			myReceiver = new MyReceiver();
			MyMsgActivity.this.registerReceiver(myReceiver, new IntentFilter(
					Const.UPDATE_MY_MSG));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(myReceiver);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		ivShowMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					menu.showMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		ivShowMenu = (ImageView) findViewById(R.id.iv_topic_showMenu);
		lv = (ListView) findViewById(R.id.lv_my_msg);
		msgAdapter = new MyMsgAdapter(this, TApplication.listMsg);
		lv.setAdapter(msgAdapter);
	}

	class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		msgAdapter.updateDate(TApplication.listMsg);
	}
	
}
}

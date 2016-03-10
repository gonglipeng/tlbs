package com.tarena.tlbs.view;

import com.tarena.tlbs.R;
import com.tarena.tlbs.biz.AddFriendService;
import com.tarena.tlbs.entity.FriendEntity;
import com.tarena.tlbs.util.Const;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddFriendActivity extends BaseActivity {
	private EditText etUserName;
	private EditText etName;
	private EditText etGroup;
	private TextView submit;
	
	MyReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friend);
		
		setupView();
		addListener();
		
		receiver=new MyReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(Const.ACTION_SEND_ADD_FRIEND);
		registerReceiver(receiver, filter);
	}
	private void setupView() {
		etUserName=(EditText)findViewById(R.id.et_add_friend_friendName);
		etName=(EditText)findViewById(R.id.et_add_friend_name);
		etGroup=(EditText)findViewById(R.id.et_add_friend_group);
		submit=(TextView)findViewById(R.id.tv_add_friend_submit);
	}
	private void addListener() {
		submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userName=etUserName.getText().toString();
				String name=etName.getText().toString();
				String group=etGroup.getText().toString();
				FriendEntity friendEntity=new FriendEntity(userName, name, group);
				Intent intent=new Intent(AddFriendActivity.this,AddFriendService.class);
				intent.putExtra(Const.KEY_DATA, friendEntity);
				startService(intent);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(AddFriendActivity.this, "添加好友信息已发送", Toast.LENGTH_LONG).show();
		}
		
	}
}

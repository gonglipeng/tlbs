package com.tarena.tlbs.view;

import java.util.Vector;

import org.jivesoftware.smack.packet.Message;

import com.tarena.tlbs.R;
import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.adapter.FaceAdapter;
import com.tarena.tlbs.biz.PrivateChatTask;
import com.tarena.tlbs.entity.PrivateChatEntity;
import com.tarena.tlbs.util.ChatUtil;
import com.tarena.tlbs.util.Const;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import net.frakbot.imageviewex.Converters;
import net.frakbot.imageviewex.ImageViewEx;

public class PrivateChatActivity extends BaseActivity {
	private String friendUser;
	private Button btSendMsg;
	private TextView tvUserName;
	private EditText etBody;
	private ShowPrivateChatReceiver showPrivateChatReceiver;
	private ScrollView scrollView;
	private LinearLayout linearLayout;
	private Handler handler = new Handler();
	TextView tvMore;
	LinearLayout linearLayoutMore;
	Button btnFace;
	GridView gridView;
	FaceAdapter faceAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.private_chat);
		getData();
		setUpView();
		addListener();
		showPrivateChatReceiver = new ShowPrivateChatReceiver();
		IntentFilter intentFilter = new IntentFilter(Const.ACTION_SEND_PRIVATE_CHAT_MSG);
		this.registerReceiver(showPrivateChatReceiver, intentFilter);
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(showPrivateChatReceiver);
	}

	private void getData() {
		// TODO Auto-generated method stub
		friendUser = getIntent().getStringExtra(Const.KEY_DATA);
	}

	private void setUpView() {
		// TODO Auto-generated method stub
		btSendMsg = (Button) findViewById(R.id.btn_private_chat_send);
		etBody = (EditText) findViewById(R.id.et_private_body);
		tvUserName = (TextView) findViewById(R.id.tv_private_chat_friendName);
		if (friendUser.contains("@")) {
			String friendName = friendUser.substring(0, friendUser.indexOf("@"));
			tvUserName.setText(friendName);
		}
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayoutChatContent);
		tvMore = (TextView) findViewById(R.id.tv_private_chat_more);
		linearLayoutMore = (LinearLayout) findViewById(R.id.linearLayoutMore);
		btnFace = (Button) findViewById(R.id.btn_private_chat_face);
		gridView = (GridView) findViewById(R.id.gridView_face);

		faceAdapter = new FaceAdapter(this);
		gridView.setAdapter(faceAdapter);
	}

	private void addListener() {
		btSendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String body = etBody.getText().toString();
					etBody.getText().clear();
					sendMessage(body);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		tvMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(linearLayoutMore.getVisibility()==View.GONE){
					linearLayoutMore.setVisibility(View.VISIBLE);
				}else if(linearLayoutMore.getVisibility()==View.VISIBLE){
					linearLayoutMore.setVisibility(View.GONE);
				}
			}
		});
		
		btnFace.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linearLayoutMore.setVisibility(View.GONE);
				if(gridView.getVisibility()==View.GONE){
					gridView.setVisibility(View.VISIBLE);
				}
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				gridView.setVisibility(View.GONE);
				String faceName=(String) faceAdapter.getItem(position);
				String body=ChatUtil.addTag(faceName);
				sendMessage(body);
			}
		});
	}
	
	private void sendMessage(String body) {
		if (!TextUtils.isEmpty(friendUser) && !TextUtils.isEmpty(body)) {
			new PrivateChatTask().execute(friendUser, body);
		}
	}

	class ShowPrivateChatReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("tag", "信息发送成功");
			Vector<Message> vector = PrivateChatEntity.map.get(friendUser);
			for (Message message : vector) {
				String from = message.getFrom();
				String body = message.getBody();
				Log.i("ShowPrivateMsgReceiver", from + ":" + body);
				View view=null;
				if(from.equals(TApplication.instance.currentUser)){
					view=View.inflate(context, R.layout.right, null);
				}else{
					view=View.inflate(context, R.layout.left, null);
				}
				linearLayout.addView(view);
				switch (ChatUtil.getType(body)) {
				case ChatUtil.TYPE_AUDIO:
					
					break;
				case ChatUtil.TYPE_FACE:
					ImageViewEx imageView=(ImageViewEx) view.findViewById(R.id.ll_gifImageView);
					String faceName=ChatUtil.getFace(body);
					imageView.setSource(Converters.assetToByteArray(context.getAssets(), faceName));
					imageView.setVisibility(View.VISIBLE);
					break;
				case ChatUtil.TYPE_IMAGE:
					
					break;
				case ChatUtil.TYPE_MAP:
					
					break;
				case ChatUtil.TYPE_TEXT:
					TextView tvText=(TextView) view.findViewById(R.id.tv_text);
					tvText.setText(body);
					tvText.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}
				
				
				Log.i("tag", "Thread");
				/*new Thread(){
					public void run() {
						try {
							Thread.sleep(10);
							Log.i("tag", "sleep");
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									moveUp();
									Log.i("tag", "moveUp");
								}
							});
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.i("tag", "InterruptedException");
						}
					};
				}.start();*/
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						moveUp();
					}
				});

				
				//每次都重新加载Vector 所以要移除 不然以前的记录会重复出现
				vector.remove(message);
			}
			
		}

		protected void moveUp() {
			int scrollViewHeight = scrollView.getHeight();
			int linearLayoutHeight = linearLayout.getHeight();
			Log.i("Height", "scrollViewHeight=" + scrollViewHeight + ",linearLayoutHeight=" + linearLayoutHeight);
			if (linearLayoutHeight > scrollViewHeight) {
				int height =  linearLayoutHeight-scrollViewHeight;
				scrollView.scrollTo(0, height);
			}
		}
	}
}

package com.tarena.tlbs.view;

import java.util.ArrayList;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import com.tarena.tlbs.R;
import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.adapter.MyFriendAdapter;
import com.tarena.tlbs.biz.PrivateChatTask;
import com.tarena.tlbs.util.Const;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyFriendActivity extends BaseActivity {
	ImageView ivShowMenu;
	Menu menu;
	TextView tvToAddFriend;
	RelativeLayout title;
	PopupWindow popupWindow;
	MyFriendAdapter myFriendAdapter;
	ExpandableListView expandableListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.my_friend);
			setupView();
			addListener();
			menu = new Menu(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		tvToAddFriend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (popupWindow==null) {
					// TODO Auto-generated method stub
					View view = View.inflate(MyFriendActivity.this, R.layout.my_friend_more, null);
					popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					popupWindow.showAtLocation(title, //与此控件对齐
							Gravity.TOP | Gravity.RIGHT, //在右上角
							0, title.getHeight() + 140); //相对控件(0,0)的x，y坐标
					Button btnAddFriend = (Button) view.findViewById(R.id.btn_my_friend_more_addFriend);
					btnAddFriend.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(MyFriendActivity.this, AddFriendActivity.class);
							startActivity(intent);
							popupWindow.dismiss();
							popupWindow=null;
						}
					});
				}else{
					popupWindow.dismiss();
					popupWindow=null;
				}
			}
		});
		
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				try {
					RosterEntry rosterEntry=(RosterEntry)myFriendAdapter.getChild(groupPosition, childPosition);
					String friendName=rosterEntry.getUser();
					/*new PrivateChatTask().execute(friendName,"nihao");*/
					Intent intent=new Intent(MyFriendActivity.this,PrivateChatActivity.class);
					intent.putExtra(Const.KEY_DATA, friendName);
					startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(popupWindow!=null && popupWindow.isShowing()){
				popupWindow.dismiss();
				popupWindow=null;
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void setupView() {
		// TODO Auto-generated method stub
		ivShowMenu = (ImageView) findViewById(R.id.iv_topic_showMenu);
		tvToAddFriend=(TextView)findViewById(R.id.tv_myFriend_to_addFriend);
		title=(RelativeLayout)findViewById(R.id.title);
		expandableListView=(ExpandableListView)findViewById(R.id.expandableListView1);
		//花名册
		Roster roster=TApplication.instance.xmppConnection.getRoster();
		ArrayList<RosterGroup> rosterGroup=new ArrayList<RosterGroup>(roster.getGroups());
		myFriendAdapter=new MyFriendAdapter(this,rosterGroup);
		expandableListView.setAdapter(myFriendAdapter);
	}

}

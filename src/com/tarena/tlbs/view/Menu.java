package com.tarena.tlbs.view;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tarena.tlbs.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu {
	Activity activity;
	// ��ʾleftMenu.xml
	SlidingMenu slidingMenu;
	Button[] btnArray = new Button[7];
	Class[] classArray = { TopicActivity.class, NearbyTopicActivity.class,
			TopicActivity.class, MyFriendActivity.class, MyMsgActivity.class,
			MyResActivity.class, SettingActivity.class };

	public Menu(final Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		slidingMenu = new SlidingMenu(activity);
		// ��xml���view
		View view = View.inflate(activity, R.layout.leftmenu, null);
		slidingMenu.setMenu(view);
		slidingMenu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
		// �໬�˵��ұ�����bhindoffset
		int screenWidth = activity.getWindowManager().getDefaultDisplay()
				.getWidth();

		slidingMenu.setBehindOffset(screenWidth * 18 / 100);

		btnArray[0] = (Button) view.findViewById(R.id.btn_leftmenu_allTopic);
		btnArray[1] = (Button) view.findViewById(R.id.btn_leftmenu_nearbyTopic);
		btnArray[2] = (Button) view.findViewById(R.id.btn_leftmenu_my_topic);
		btnArray[3] = (Button) view.findViewById(R.id.btn_leftmenu_my_friend);
		btnArray[4] = (Button) view.findViewById(R.id.btn_leftmenu_my_msg);
		btnArray[5] = (Button) view.findViewById(R.id.btn_leftmenu_my_res);
		btnArray[6] = (Button) view.findViewById(R.id.btn_leftmenu_setting);

		for (int i = 0; i < btnArray.length; i++) {
			final int index = i;
			btnArray[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(activity, classArray[index]);
						activity.startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public void showMenu() {
		slidingMenu.showMenu();
	}

	public void close() {

	}

}

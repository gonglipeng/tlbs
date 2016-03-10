package com.tarena.tlbs.view;

import com.tarena.tlbs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class NearbyTopicActivity extends BaseActivity {
	ImageView ivShowMenu;
	Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.nearby_topic);
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
	}

	private void setupView() {
		// TODO Auto-generated method stub
		ivShowMenu = (ImageView) findViewById(R.id.iv_topic_showMenu);
	}

}

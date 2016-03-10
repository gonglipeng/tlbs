package com.tarena.tlbs.view;

import com.tarena.tlbs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TopicActivity extends BaseActivity {
	ImageView ivShowMenu;

	Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.topic);
			setupView();
			addListener();
			menu = new Menu(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addListener() {
		ivShowMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.showMenu();
			}
		});
	}

	private void setupView() {
		ivShowMenu = (ImageView) findViewById(R.id.iv_topic_showMenu);

	}

}

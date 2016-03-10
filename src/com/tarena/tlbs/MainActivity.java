package com.tarena.tlbs;

import org.jivesoftware.smack.XMPPConnection;

import com.tarena.tlbs.biz.LoginBiz;
import com.tarena.tlbs.entity.UserEntity;
import com.tarena.tlbs.util.Tools;
import com.tarena.tlbs.view.BaseActivity;
import com.tarena.tlbs.view.LoginActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	TextView tvVersionName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// testLoginBiz();
		setContentView(R.layout.activity_main);
		tvVersionName = (TextView) findViewById(R.id.tv_activity_main_versionName);
		tvVersionName.setText(Tools.getVersionName(this));

		new Thread() {
			public void run() {
				try {
					int threadid = (int) this.getId();

					this.sleep(5000);
				} catch (Exception e) {
					// TODO: handle exception
				}

				// 相当于发消息
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						int threadid = (int) Thread.currentThread().getId();
						startActivity(new Intent(MainActivity.this, LoginActivity.class));
						finish();
					}
				});
			};
		}.start();
	}

	private void testLoginBiz() {
		// TODO Auto-generated method stub
		UserEntity entity = new UserEntity("a1409zhangjiujun", "1");
		new LoginBiz().login(entity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package com.tarena.tlbs.view;

import com.tarena.tlbs.R;
import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.biz.LoginBiz;
import com.tarena.tlbs.entity.UserEntity;
import com.tarena.tlbs.util.Const;
import com.tarena.tlbs.util.NetworkUtil;
import com.tarena.tlbs.util.Tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
	EditText etUsername, etPassword;
	TextView tvSubmit, tvToRegister;
	MyReceiver myReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try {
			NetworkUtil.OpenNetworkSetting(this);
			setContentView(R.layout.login);
			setViews();
			addListener();
			myReceiver = new MyReceiver();
			IntentFilter intentFilter = new IntentFilter(Const.ACTION_LOGIN);
			this.registerReceiver(myReceiver, intentFilter);
		} catch (Exception e) {
			// TODO: handle exception
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
		MyOnClickListener myOnClickListener = new MyOnClickListener();
		tvSubmit.setOnClickListener(myOnClickListener);
		tvToRegister.setOnClickListener(myOnClickListener);
	}

	private void setViews() {
		// TODO Auto-generated method stub
		etUsername = (EditText) findViewById(R.id.et_login_username);
		etPassword = (EditText) findViewById(R.id.et_login_password);
		tvSubmit = (TextView) findViewById(R.id.tv_login_submit);
		tvToRegister = (TextView) findViewById(R.id.tv_login_toRegister);
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			try {
				switch (v.getId()) {
				case R.id.tv_login_submit:
					doSubmit();
					break;
				case R.id.tv_login_toRegister:
					doRegister();
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void doRegister() {
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
		}

		private void doSubmit() {
			Tools.showProgressDialog(LoginActivity.this, "亲，正在努力登录....");
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();

			StringBuilder builder = new StringBuilder();
			if (TextUtils.isEmpty(username)) {
				builder.append("用户名不能为空\n");
			}
			if (TextUtils.isEmpty(password)) {
				builder.append("密码不能为空\n");
			}
			if (!TextUtils.isEmpty(builder.toString())) {
				// 前面的数据检查没有通过
				Toast.makeText(LoginActivity.this, builder.toString(), 2000)
						.show();
				return;
			}

			UserEntity userEntity = new UserEntity(username, password);
			LoginBiz loginBiz = new LoginBiz();
			loginBiz.login(userEntity);

			// 设置控件不响应单击
			tvSubmit.setEnabled(false);
		}

	}

	class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			tvSubmit.setEnabled(true);

			Tools.closeProgressDialog();
			int status = intent.getIntExtra(Const.KEY_DATA, -1);
			String msg = "";
			switch (status) {
			case Const.STATUS_OK:
				msg = "登录成功";
				startActivity(new Intent(context, TopicActivity.class));
				finish();
				break;
			case Const.STATUS_CONNECT_FAILURE:
				msg = "亲，对不起。连接服务器失败!!!";
				break;
			case Const.STATUS_LOGIN_FAILURE:
				msg = "登录失败";
				break;
			}
			Toast.makeText(context, msg, 3000).show();

		}

	}
}

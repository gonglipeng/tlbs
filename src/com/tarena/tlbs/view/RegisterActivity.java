package com.tarena.tlbs.view;

import com.tarena.tlbs.R;
import com.tarena.tlbs.biz.RegisterBiz;
import com.tarena.tlbs.entity.UserEntity;
import com.tarena.tlbs.util.Const;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
	EditText etUsername, etPassword, etConfirmPassword, etName;
	TextView tvSubmit,tvToLogin;
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// biz����������״̬�룺ok�ǳɹ����û����Ѿ�����
			// �ӹ����̵߳õ����������ַ�ʽ
			// 1, msg.obj
			// 2,msg.getData()
			Bundle bundle = msg.getData();
			int status = bundle.getInt(Const.KEY_DATA);
			if (status == Const.STATUS_OK) {
				Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_LONG)
						.show();
			}else if(status==2){
				Toast.makeText(RegisterActivity.this, "���û���ע��", Toast.LENGTH_LONG).show();
			}else if(status==3){
				Toast.makeText(RegisterActivity.this, "ע��ʧ��", Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.register);
			setupView();
			addListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addListener() {
		// TODO Auto-generated method stub
		tvSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String username = etUsername.getText().toString();
					String password = etPassword.getText().toString();
					String name = etName.getText().toString();
					// ���ѧԱ���

					// ��biz
					RegisterBiz registerBiz = new RegisterBiz();
					registerBiz.register(handler,new UserEntity(username, password,
							name));

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		tvToLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		etUsername = (EditText) findViewById(R.id.et_register_username);
		etPassword = (EditText) findViewById(R.id.et_register_password);
		etConfirmPassword = (EditText) findViewById(R.id.et_register_confirmPassword);
		etName = (EditText) findViewById(R.id.et_register_name);

		tvSubmit = (TextView) findViewById(R.id.tv_register_submit);
		tvToLogin = (TextView) findViewById(R.id.tv_register_toLOgin);
	}

}

package com.tarena.tlbs.view;

import java.io.File;

import com.tarena.tlbs.R;
import com.tarena.tlbs.TApplication;
import com.tarena.tlbs.biz.UpdateBiz;
import com.tarena.tlbs.entity.UpdateEntity;
import com.tarena.tlbs.util.Const;
import com.tarena.tlbs.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends BaseActivity {

	// ��ʾ�汾����Ϣ(msg.what�еĲ���)
	public static final int MSG_SHOW_VERSION = 0;
	public static final int MSG_INSTALL_APK = 1;
	ImageView ivShowMenu;
	Menu menu;
	Button btnExit;
	Button btnUpdate; // ���°�ť
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_SHOW_VERSION:
				Bundle bundle = msg.getData();
				showUpdateMsg((UpdateEntity) bundle.get(Const.KEY_DATA));
				break;
			case MSG_INSTALL_APK:
				Bundle bundle2=msg.getData();
				String pathFile=bundle2.getString(Const.KEY_DATA);
				install(pathFile);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.setting);
			setupView();
			addListener();
			menu = new Menu(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void install(String pathFile) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Intent.ACTION_VIEW);
		File fileApk=new File(pathFile);
		intent.setDataAndType(Uri.fromFile(fileApk) ,"application/vnd.android.package-archive");
		startActivity(intent);
	}

	// ��ʾ������Ϣ
	protected void showUpdateMsg(final UpdateEntity updateEntity) {
		if (updateEntity == null) {
			Toast.makeText(SettingActivity.this, "�Բ��𣬸���ʧ��", Toast.LENGTH_LONG).show();
		} else {
			double updateVersion = Double.parseDouble(updateEntity.getVersion());
			double currentVersion = Double.parseDouble(Tools.getVersionName(SettingActivity.this));
			if (currentVersion >= updateVersion) {
				Toast.makeText(SettingActivity.this, "����ǰʹ�õ������°汾", Toast.LENGTH_LONG).show();
			} else {
				AlertDialog.Builder dialog = new Builder(SettingActivity.this);
				dialog.setMessage(
						"�汾��:" + updateEntity.getVersion() + "\n" + updateEntity.getChangeLog() + "\n 99%���û��Ѿ�������!");
				dialog.setPositiveButton("��������", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						new UpdateBiz().getApk(handler, updateEntity.getApkUrl());
					}

				});
				dialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.show();
			}
		}
	}

	private void addListener() {
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TApplication.instance.exit();
			}
		});
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
		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new UpdateBiz().getVersion(handler);
			}
		});
	}

	private void setupView() {
		// TODO Auto-generated method stub
		ivShowMenu = (ImageView) findViewById(R.id.iv_topic_showMenu);
		btnExit = (Button) findViewById(R.id.btn_setting_exit);
		btnUpdate = (Button) findViewById(R.id.btn_setting_update);
	}

}

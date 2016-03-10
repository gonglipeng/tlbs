package com.tarena.tlbs.util;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	public static void OpenNetworkSetting(final Activity activity) {
		// �����û������
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		//ģ�������õ����������п������÷���ģʽactiveNetworkInfo��=null
		//��������ԡ����ҲҪ����
		//��һ̨�ʼǱ����ԣ���wifi����
		if (activeNetworkInfo == null) {
			// û������ʾһ��dialog,
			AlertDialog.Builder dialog = new Builder(activity);
			dialog.setMessage("�ף�������û��");
			// ��
			dialog.setPositiveButton("��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						//��ͬ��android�汾�������ý���activity�е�intent-filetr,action�ǲ�һ����
						//��ͬ��android�汾�Ĵ����ǲ�һ���ġ�
						int androidVersion=android.os.Build.VERSION.SDK_INT;
						//ͨ������õ��ֻ��������ƣ�
						//��ͬ���̵��ֻ��Ĵ����ǲ�һ���ġ�
						//�е��ֻ��ܵõ��ֻ��ţ��󲿷��ֻ��ܵõ�sim���еĴ��ţ�����ÿ���ֻ���Ψһ�ġ�
						if (androidVersion>=10)
						{
						// ��ϵͳ�Դ����������ý���
						Intent intent = new Intent(
								android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						activity.startActivity(intent);
						}

					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			});
			// ȡ��
			dialog.setNegativeButton("ȡ��", new OnClickListener() {

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

package com.tarena.tlbs.util;

import android.os.Environment;

public class Const {
public static final String ACTION_LOGIN="com.tarena.tlbs.loginActiviyt";
public static final String KEY_DATA="key_data";

//��¼������������,����״̬��
//��¼�ɹ�
public static final int STATUS_OK=0;
//����ʧ��
public static final int STATUS_CONNECT_FAILURE=1;

//��¼ʧ��
public static final int STATUS_LOGIN_FAILURE=2;
/**
 * �û��ر�������
 */
public static final int NETWORK_NONE=3;
public static final int NETWORK_TYPE_WIFI=4;
public static final int NETWORK_TYPE_MOBILE=4;
/**
 * sdcard��Ŀ¼����������洢Ŀ¼
 */
public static final String SDCARD_ROOT=Environment.getExternalStorageDirectory().getAbsolutePath();
public static final String APP_DATA_ROOT=SDCARD_ROOT+"/tlbs1411";//mnt/sdcard/tlbs1411
public static final String DWONLOAD_PATH=APP_DATA_ROOT+"/download";
public static final String IMAGE_PATH=APP_DATA_ROOT+"/image";
public static final String AUDIO_PATH=APP_DATA_ROOT+"/audio";
public static final String ACTION_SEND_ADD_FRIEND = "tlbs.src.com.tarena.tlbs.util.Const.ACTION_SEND_ADD_FRIEND";
public static final String UPDATE_MY_MSG = "tlbs.src.com.tarena.tlbs.util.Const.UPDATE_MY_MSG";
public static final String ACTION_SEND_PRIVATE_CHAT_MSG = "tlbs.src.com.tarena.tlbs.util.Const.ACTION_SEND_PRIVATE_CHAT_MSG";
}

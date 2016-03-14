package com.tarena.tlbs.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ChatUtil {
	// ������������
	public final static int TYPE_TEXT = 1;
	public final static int TYPE_FACE = 2;
	public final static int TYPE_IMAGE = 3;
	public final static int TYPE_AUDIO = 4;
	public final static int TYPE_MAP = 5;

	// �������ݼ�tag
	public final static String TAG_TEXT = "<!--TEXT>";
	public final static String TAG_FACE = "<!--FACE>";
	public final static String TAG_IMAGE = "<!--IMAGE>";
	public final static String TAG_AUDIO = "<!--AUDIO>";
	public final static String TAG_MAP = "<!--MAP>";
	public final static String TAG_END = "</end>";

	// �������tag
	public static String addTag(String faceName) {
		StringBuilder builder = new StringBuilder();
		builder.append(TAG_FACE).append(faceName).append(TAG_END);
		return String.valueOf(builder);
	}

	// �ж��յ�����������
	public static int getType(String body) {
		if (body.startsWith(TAG_AUDIO)) {
			return TYPE_AUDIO;
		} else if (body.startsWith(TAG_FACE)) {
			return TYPE_FACE;
		} else if (body.startsWith(TAG_IMAGE)) {
			return TYPE_IMAGE;
		} else if (body.startsWith(TAG_MAP)) {
			return TYPE_MAP;
		}
		return TYPE_TEXT;
	}

	// ȡ�����id<!--face>7052</end>
	public static String getFace(String body) {
		int start = TAG_FACE.length();
		int end = body.length() - TAG_END.length();
		String faceId = body.substring(start, end);
		return faceId;
	}

	/**
	 * ��ͼƬʱ�����
	 * 
	 * @param imageData
	 * @return
	 *  ��byte[]ת���ַ���
	 *	1,��java�ķ�ʽ,���ַ���ת��byte,�б�������
	 * 	 ��������õ���iphone,������
	 *	String body=new String(imageData);
	 *	�����յ�����body
	 *	byte[] data=body.getBytes();
	 *	2,��Ҫһ�ֱ��뷽ʽBase64��ͨ�õģ�c,java,iphone��֧��
	 */
	public static String addImageTag(byte [] imageData){
		StringBuilder stringBuilder=new StringBuilder();
		try {
			//����Base64���룺���ֽڱ�����ַ���
			String string=Base64.encodeToString(imageData, Base64.DEFAULT);
			stringBuilder.append(TAG_IMAGE).append(string).append(TAG_END);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuilder.toString();
		}
/**
 * ����ͼƬʱ����
 * 
 * @param body
 * @return
 */
	public static Bitmap getImage(String body){
		int start=TAG_IMAGE.length();
		int end=body.length()-TAG_END.length();
		body=body.substring(start, end);
		//����Base64���뽫�ַ���������ֽ�
		byte [] bs=Base64.decode(body, Base64.DEFAULT);
		Bitmap bitmap=BitmapFactory.decodeByteArray(bs, 0, bs.length);
		return bitmap;
	}
}

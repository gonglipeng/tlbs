package com.tarena.tlbs.util;

import android.graphics.Bitmap;

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
	public static String imageToCode(){
		return null;
		}

}

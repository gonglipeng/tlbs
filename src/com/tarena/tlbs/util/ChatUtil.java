package com.tarena.tlbs.util;

import android.graphics.Bitmap;

public class ChatUtil {
	// 五种数据类型
	public final static int TYPE_TEXT = 1;
	public final static int TYPE_FACE = 2;
	public final static int TYPE_IMAGE = 3;
	public final static int TYPE_AUDIO = 4;
	public final static int TYPE_MAP = 5;

	// 五种数据家tag
	public final static String TAG_TEXT = "<!--TEXT>";
	public final static String TAG_FACE = "<!--FACE>";
	public final static String TAG_IMAGE = "<!--IMAGE>";
	public final static String TAG_AUDIO = "<!--AUDIO>";
	public final static String TAG_MAP = "<!--MAP>";
	public final static String TAG_END = "</end>";

	// 给表情加tag
	public static String addTag(String faceName) {
		StringBuilder builder = new StringBuilder();
		builder.append(TAG_FACE).append(faceName).append(TAG_END);
		return String.valueOf(builder);
	}

	// 判断收到的数据类型
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

	// 取表情的id<!--face>7052</end>
	public static String getFace(String body) {
		int start = TAG_FACE.length();
		int end = body.length() - TAG_END.length();
		String faceId = body.substring(start, end);
		return faceId;
	}

	/**
	 * 发图片时候调用
	 * 
	 * @param imageData
	 * @return
	 *  把byte[]转成字符串
	 *	1,用java的方式,把字符串转成byte,有编码问题
	 * 	 如果好友用的是iphone,出问题
	 *	String body=new String(imageData);
	 *	好友收到的是body
	 *	byte[] data=body.getBytes();
	 *	2,需要一种编码方式Base64是通用的，c,java,iphone都支持
	 */
	public static String imageToCode(){
		return null;
		}

}

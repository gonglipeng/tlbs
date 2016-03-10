package com.tarena.tlbs.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderUtil {
	private static ImageLoaderConfiguration imageLoaderConfiguration;

	public static ImageLoaderConfiguration getConfig(Context context) {
		if (imageLoaderConfiguration == null) {
			imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
					context).discCacheSize(1024 * 1024 * 100). // ����ͼƬ��󻺴�ռ�
					discCacheFileCount(50) // 50���߳�
					.build();
		}
		return imageLoaderConfiguration;
	};

	public static void displayNetImage(Context context, String imageUrl,
			ImageView imageView) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(getConfig(context));
		imageLoader.displayImage(imageUrl, imageView,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub
						Log.i("tag", "onLoadingStarted");
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub
						Log.i("tag", "onLoadingFailed");
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub
						Log.i("tag", "onLoadingCancelled");
					}
				});
	}

	public static void displaySdcardImage(Context context, String imagePath,
			ImageView imageView) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderUtil.getConfig(context));
		// ���imageUrl����http://��ʼ�ģ�����������ͼƬ
		// ���imageUrl����file://��ʼ��,��sdcard�϶�ͼƬ
		imagePath = "file://" + imagePath;
		imageLoader.displayImage(imagePath, imageView);
	}
}

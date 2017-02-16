package com.lxc.immersiveslidemenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DensityUtil {

	/**
	 * 根据手机的分辨率为dip的单位转换成px像素
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率为px像素转换成dip的单位
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @date 2013年7月23日
	 * @param context
	 * @return
	 */
	public static int getWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @date 2013年7月23日
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	// 获取屏幕尺寸，不包括虚拟功能高度
	public static int getHeight(View view) {
		int location[] = new int[2];
		view.getLocationOnScreen(location);
		return 0;
	}

	/**
	 * @param urlpath
	 * @return Bitmap 根据url获取布局背景的对象
	 */
	public static void getDrawable(final String urlpath, final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(urlpath);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream in;
					in = conn.getInputStream();
					Drawable d = Drawable.createFromStream(in, "background.jpg");
					Message msg = handler.obtainMessage();
					msg.obj = d;
					handler.sendMessage(msg);
					// TODO Auto-generated catch block
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
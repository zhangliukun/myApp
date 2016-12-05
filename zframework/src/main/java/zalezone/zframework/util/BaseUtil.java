package zalezone.zframework.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

/**
 * ClassName:BaseUtil Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author jack.qin
 * @version
 * @since Ver 1.1
 * @Date 2015-4-20 下午3:54:37
 * 
 * @see
 */
public class BaseUtil
{

	public static int dp2px(Context context, float dipValue)
	{
		if(context==null)
			return 0;
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int sp2px(Context context, float dipValue)
	{
		if(context==null)
			return 0;
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * get the width of the device screen
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * get the height of the device screen
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取状态栏的高度
	 */
	public static int getStatusBarHeight(Context context)
	{
		int statusBarHeight = 0;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object obj = clazz.newInstance();
			Field field = clazz.getField("status_bar_height");
			int temp = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources()
					.getDimensionPixelSize(temp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusBarHeight;
	}
	

	/**
	 * 是否是平板
	 * @param context
	 * @return
     */
	public static boolean isTabletDevice(Context context) {
		if(context == null) {
			return false;
		}
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
				Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	//字节转换为KB或MB
	public static String byteToMb(double bytes)
	{
		String result;
		double kb=bytes/1024;
		if(kb>1024)
		{
			double mb=kb/1024;
			DecimalFormat df = new DecimalFormat("0.0");
			result = df.format(mb)+"MB/s";
			Log.e("byteToMb", "1");
		}
		else
		{
			DecimalFormat df = new DecimalFormat("0.0");
			result = df.format(bytes/1024)+"KB/s";
			Log.e("byteToMb", "2");
		}
		Log.e("byteToMb", result);
		return result;
	}

	// 跳转到app的设置页面
	public static void showAppDetails(Context context, String packageName) {
		Intent intent = new Intent();
		if(Build.VERSION.SDK_INT > 9) {
			intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			Uri uri = Uri.fromParts("package", packageName, null);
			intent.setData(uri);
			context.startActivity(intent);
		}
	}

}

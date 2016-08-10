package zalezone.zframework.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SharedPreferencesUtil {
	public static final String SHARED_NAME = "ting_data";

	private static SharedPreferencesUtil instance;
	private SharedPreferences settings;
	private Context mContext;

	private SharedPreferencesUtil(Context context)
	{
		mContext = context;
		
		settings = context.getSharedPreferences(SHARED_NAME,Context.MODE_MULTI_PROCESS|Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
	}

	public static SharedPreferencesUtil getInstance(Context context) {

		if (instance == null) {
			instance = new SharedPreferencesUtil(context);
		}

		return instance;

	}

	public void saveLong(String key, long value) {
		apply(settings.edit().putLong(key, value));
	}
	
	public void saveFloat(String key,float value) {
		apply(settings.edit().putFloat(key, value));
	}
	
	public float getFloat(String key) {
		return settings.getFloat(key, -1);
	}
	
	@SuppressLint("NewApi")
	public void apply(Editor editor) {
		// 大于等于android2.3
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			editor.apply();
		} else {
			editor.commit();
		}
	}

	/**
	 * default -1
	 * */
	public long getLong(String key) {
		return settings.getLong(key, -1);
	}

	public void saveString(String key, String value) {
		apply(settings.edit().putString(key, value));
	}

	public String getString(String key) {
		return settings.getString(key, "");
	}

	public void saveInt(String key, int value) {
		apply(settings.edit().putInt(key, value));
	}

	public int getInt(String key, int defaultValue) {
		return settings.getInt(key, defaultValue);
	}

	public Double getOptDouble(String key) {
		String retStr = settings.getString(key, null);
		Double ret = null;
		try {
			ret = Double.parseDouble(retStr);
		} catch (Exception e) {
		}
		return ret;
	}

	public Boolean getOptBoolean(String key) {
		String retStr = settings.getString(key, null);
		Boolean ret = null;
		try {
			ret = Boolean.parseBoolean(retStr);
		} catch (Exception e) {
		}
		return ret;
	}

	public Double getDouble(String key) {
		String retStr = settings.getString(key, null);
		Double ret = null;
		try {
			if (retStr != null) {
				ret = Double.parseDouble(retStr);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		return ret;
	}

	public void saveHashMap(final String key, HashMap<String, String> map) {
		final JSONObject ret = new JSONObject(map);
		apply(settings.edit().putString(key, ret.toString()));
	}

	public HashMap<String, String> getHashMapByKey(String key) {
		HashMap<String, String> ret = new HashMap<String, String>();
		String mapStr = settings.getString(key, "{}");
		JSONObject mapJson = null;
		try {
			mapJson = new JSONObject(mapStr);
		} catch (Exception e) {
			return ret;
		}

		if (mapJson != null) {
			Iterator<String> it = mapJson.keys();
			while (it.hasNext()) {
				String theKey = it.next();
				String theValue = mapJson.optString(theKey);
				ret.put(theKey, theValue);
			}
		}
		return ret;
	}

	public void saveBoolean(String key, boolean bool) {
		apply(settings.edit().putBoolean(key, bool));
	}

	public boolean getBoolean(String key) {
		return settings.getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean is) {
		return settings.getBoolean(key, is);
	}

	public void saveArrayList(String key, ArrayList<String> list) {
		apply(settings.edit().putString(key, new Gson().toJson(list)));
	}
	
	public ArrayList<String> getArrayList(String key) {
		ArrayList<String> ret = new ArrayList<String>();
		String listStr = settings.getString(key, "{}");
		JSONArray listJson = null;
		try {
			listJson = new JSONArray(listStr);
		} catch (Exception e) {
			return ret;
		}

		if (listJson != null) {
			for (int i = 0; i < listJson.length(); i++) {
				String temp = listJson.optString(i);
				ret.add(temp);
			}
		}
		return ret;
	}

	public void removeByKey(String key) {
		apply(settings.edit().remove(key));
	}

	public boolean contains(String alarmHour) {
		return settings.contains(alarmHour);
	}
}

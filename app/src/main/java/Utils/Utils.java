package Utils;

import android.app.Activity;
import android.os.BatteryManager;
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

import static android.content.Context.BATTERY_SERVICE;

/**
 * 项目名称：Cartoon8
 * 类描述：工具类
 * 创建人：SmileSB101
 * 创建时间：2017/3/13 0013 10:38
 * 修改人：Administrator
 * 修改时间：2017/3/13 0013 10:38
 * 修改备注：
 */

public final class Utils{
	private static final String TAG = "Utils";

	public static final String DATATYPE_INT = "int";
	public static final String DATATYPE_LONG = "long";

	/**
	 * 获取手机电池信息
	 * @param activity 活动
	 * @param infoId 信息id
	 * @param dataType 返回的类型
	 * @return
	 */
	public static int getPhoneBatteryInfo(Activity activity,int infoId,String dataType)
	{
		BatteryManager bm = (BatteryManager)activity.getSystemService(BATTERY_SERVICE);
		switch(dataType)
		{
			case DATATYPE_INT:
				return bm.getIntProperty(infoId);
			case DATATYPE_LONG:
				default:
					return bm.getIntProperty(infoId);
		}
	}

	public static String getTimeHour()
	{
		Calendar calendar = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		//Log.i(TAG,"getTimeHour: "+calendar.get(Calendar.HOUR_OF_DAY));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(hour>12)
		{
			sb.append("下午 ");
			hour -= 12;
		}
		else
		{
			sb.append("上午");
		}
		sb.append(hour);
		sb.append(":");
		int minute = calendar.get(Calendar.MINUTE);
		if(minute<10)
		{
			sb.append("0");
		}
		sb.append(minute);
		return sb.toString();
	}
}

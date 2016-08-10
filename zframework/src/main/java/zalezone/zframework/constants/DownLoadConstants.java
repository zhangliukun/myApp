/**
 * DownLoadConstants.java
 * com.ximalaya.ting.android.framework.constants
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-7-14 		jack.qin
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package zalezone.zframework.constants;

/**
 * ClassName:DownLoadConstants Function: TODO ADD FUNCTION Reason: TODO ADD
 * REASON
 * 
 * @author jack.qin
 * @version
 * @since Ver 1.1
 * @Date 2015-7-14 下午4:35:16
 * 
 * @see
 */
public class DownLoadConstants
{
	public static final int DOWNLOAD_TODO = -1;
	public static final int DOWNLOAD_WAITING = 0;
	public static final int DOWNLOADING = 1;
	public static final int DOWNLOAD_PAUSE = 2;
	public static final int DOWNLOAD_FAILED = 3;
	public static final int DOWNLOAD_FINISH = 4;

	/** downloadstatus */
	public static final int WAITING_FOR_DOWNLOAD_STATE = 1;
	public static final int PAUSE_STATE = 2;
	public static final int DOWNLOADING_STATE = 3;

	/** trackstatus */
	public static final int NOT_IN_DOWNLOADLIST = 1;
	public static final int IN_DOWNLOADLIST = 2;
	public static final int DOWNLOADED = 3;

	public final static int JOIN_DOWNLOAD_SUCCESS = 0x001;// 加入下载队列成功
	public final static int SAVE_FAILED = 0x002;// 数据库存储失败
	public final static int DUPLICATE_DOWNLOAD = 0x003;// 重复下载
	public final static int NETWORK_TYPE_NONE = 0x004;// 没有网络
	public final static int INVALID_URL = 0x005;// 无效的链接
	public final static int NO_SDCARD_DETECTED = 0x006;// 没有检测到SD卡
	public final static int EMPTY_LIST = 0x007;// 列表為空
	public final static int DECIDE_SDCARD_LOCATION = 0x008;// 选择下载目录
	public final static int ADD_TO_THREADPOOL_FAILED = 0x009;// 加入到线程池失败
	public final static int ERROR_DATA = 0x0010;// 错误的数据

	public enum AddResult
	{
		ENUM_SUCCESS("加入下载列表成功", JOIN_DOWNLOAD_SUCCESS), ENUM_SAVE_FAILED(
				"数据库存储失败", SAVE_FAILED), ENUM_DUMP_TASK("不能重复下载",
				DUPLICATE_DOWNLOAD), ENUM_NONE_NETWORK("没有可用网络",
				NETWORK_TYPE_NONE), ENUM_INVALID_URL("无效的链接", INVALID_URL), ENUM_NO_SDCARD(
				"检测不到SD卡", NO_SDCARD_DETECTED), ENUM_EMPTY_LIST("空列表",
				EMPTY_LIST), ENUM_DECIDE_SDCARD("请选择下载目录",
				DECIDE_SDCARD_LOCATION), ENUM_ADD_THREADPOOL_FAILED("添加到线程池失败",
				ADD_TO_THREADPOOL_FAILED), ENUM_ERROR_DATA("错误的数据", ERROR_DATA);

		private String name;
		private int index;

		private AddResult(String name, int index)
		{
			this.name = name;
			this.index = index;
		}

		public static String getName(int index)
		{
			for (AddResult c : AddResult.values())
			{
				if (c.getIndex() == index)
				{
					return c.name;
				}
			}
			return null;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public int getIndex()
		{
			return index;
		}

		public void setIndex(int index)
		{
			this.index = index;
		}
	}
	
	public static final int MESSAGE_TYPE_REFRESH_UNFINISHED_DOWNLOAD_LIST = 0x7fff1006;
	public static final int MESSAGE_TYPE_REFRESH_FINISHED_DOWNLOAD_LIST = 0x7fff1005;
}

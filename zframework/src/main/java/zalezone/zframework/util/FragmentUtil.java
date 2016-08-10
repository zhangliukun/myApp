package zalezone.zframework.util;

import android.support.v4.app.Fragment;

public class FragmentUtil {

	public static void pauseOrResumeFragment(Fragment fragment, boolean isPause)
	{
		pauseOrResume(fragment, isPause);

		if (fragment != null &&fragment.isAdded()&& fragment.getChildFragmentManager() != null
				&& fragment.getChildFragmentManager().getFragments() != null)
		{
			for (Fragment fra : fragment.getChildFragmentManager()
					.getFragments())
			{
				if (fra == null||!fra.isAdded()) continue;
//				pauseOrResume(fra, isPause);
				pauseOrResumeFragment(fra, isPause);
			}
		}
	}

	private static void pauseOrResume(Fragment fra, boolean isPause) {
		if (fra == null||!fra.isAdded()) return;
		if (isPause) {
			fra.onPause();
		}
		else
		{
			fra.onResume();
		}
	}

	/**
	 * 测试Fragment 隐藏Fragment 节省ui绘制的方案
	 * @param fra
	 * @param hide
     */
	public static void hideOrShowFragment(Fragment fra, boolean hide) {
		if (fra == null||!fra.isAdded()) return;
		if (hide) {
			if (fra.isHidden()) return;
			fra.getFragmentManager().beginTransaction().hide(fra).commitAllowingStateLoss();
		} else {
			if (!fra.isHidden()) return;
			fra.getFragmentManager().beginTransaction().show(fra).commitAllowingStateLoss();
		}
	}
}

package zalezone.zframework.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.util.List;

public class FraManager {

    static final String TAG = FraManager.class.getSimpleName();

    static final String ARG_RESULT_RECORD = "fragment_arg_result_record";

    static final String ARG_IS_ROOT = "fragmentation_arg_is_root";
    static final String ARG_IS_SHARED_ELEMENT = "fragmentation_arg_is_shared_element";
    static final String FRAGMENTATION_ARG_CONTAINER = "fragmentation_arg_container";

    static final String FRAGMENTATION_STATE_SAVE_ANIMATOR = "fragmentation_state_save_animator";
    static final String FRAGMENTATION_STATE_SAVE_IS_HIDDEN = "fragmentation_state_save_status";

    private Activity mActivity;

    public static final int TYPE_ADD = 0;
    public static final int TYPE_ADD_WITH_POP = 1;
    public static final int TYPE_ADD_RESULT = 2;

    public FraManager(Activity activity) {
        this.mActivity = activity;
    }


    public void loadRootTransaction(FragmentManager fragmentManager, int containerId, BaseFragment to){
        bindContainerId(containerId, to);
        dispatchStartTransaction(fragmentManager,null,to,TYPE_ADD,null);
    }


    public void dispatchStartTransaction(FragmentManager fragmentManager, BaseFragment from, BaseFragment to,
                                  int type, String name) {
        if (from != null) {
            bindContainerId(from.getContainerId(), to);
        }

        switch (type) {
            case TYPE_ADD:
            case TYPE_ADD_RESULT:
                start(fragmentManager,from,to,name);
                break;
        }
    }

    public void replaceTransaction(FragmentManager fragmentManager, int containerId, BaseFragment to, boolean addToBack){
        bindContainerId(containerId,to);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId,to,to.getClass().getName());
        if (addToBack){
            ft.addToBackStack(to.getClass().getName());
        }
        Bundle bundle = to.getArguments();
        bundle.putBoolean(ARG_IS_ROOT,true);
        ft.commitAllowingStateLoss();
    }

    public  void showHideFragment(FragmentManager fragmentManager, BaseFragment showFragment, BaseFragment hideFragment){
        if (showFragment == hideFragment) return;
        fragmentManager.beginTransaction().show(showFragment).hide(hideFragment).commitAllowingStateLoss();
    }

    private void bindContainerId(int containerId, BaseFragment to) {
        Bundle args = to.getArguments();
        if (args == null) {
            args = new Bundle();
            to.setArguments(args);
        }
        args.putInt(FRAGMENTATION_ARG_CONTAINER, containerId);
    }

    public void start(FragmentManager fragmentManager, BaseFragment from, BaseFragment to, String name) {
        String toName = to.getClass().getName();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (from == null) {
            ft.add(to.getArguments().getInt(FRAGMENTATION_ARG_CONTAINER), to, toName);
            Bundle bundle = to.getArguments();
            bundle.putBoolean(ARG_IS_ROOT, true);
        } else {
            ft.add(from.getContainerId(), to, toName);
            ft.hide(from);
        }

        ft.addToBackStack(toName);
        ft.commitAllowingStateLoss();
    }

    public <T extends BaseFragment> T findStackFragment(Class<T> fragmentClass,FragmentManager fragmentManager){
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        if (fragment == null){
            return null;
        }
        return (T) fragment;
    }

//    void startWithPop(FragmentManager fragmentManager, BaseFragment from, BaseFragment to) {
//        BaseFragment preFragment = getPreFragment(from);
//        fragmentManager.beginTransaction().remove(from).commit();
//    }

    public void back(FragmentManager fragmentManager){
        if (fragmentManager == null) return;
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 1){
            String name = fragmentManager.getFragments().get(count-1).getClass().toString();
            boolean isSuccess = fragmentManager.popBackStackImmediate();
            Toast.makeText(mActivity,name+isSuccess,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取目标Fragment的前一个Fragment
     *
     * @param fragment 目标Fragment
     */
    public BaseFragment getPreFragment(Fragment fragment) {
        List<Fragment> fragmentList = fragment.getFragmentManager().getFragments();
        if (fragmentList == null) return null;
        int index = fragmentList.indexOf(fragment);
        for (int i = index - 1; i >= 0; i--) {
            Fragment preFragment = fragmentList.get(i);
            if (preFragment instanceof BaseFragment){
                return (BaseFragment) preFragment;
            }
        }
        return null;
    }

    /**
     * 获得栈顶SupportFragment
     */
    public BaseFragment getTopFragment(FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList == null) return null;
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseFragment) {
                return (BaseFragment) fragment;
            }
        }
        return null;
    }

    /**
     * 从栈顶开始查找,状态为show & userVisible的Fragment
     */
    public BaseFragment getTopActiveFragment(BaseFragment parentFragment,FragmentManager fragmentManager){
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList == null){
            return parentFragment;
        }
        for (int i = fragmentList.size()-1;i>=0;i--){
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseFragment){
                BaseFragment baseFragment = (BaseFragment) fragment;
                if (!baseFragment.isHidden()&&baseFragment.getUserVisibleHint()){
                    return getTopActiveFragment(baseFragment,baseFragment.getChildFragmentManager());
                }
            }
        }
        return parentFragment;
    }

     public boolean dispatchBackPressEvent(BaseFragment activeFragment){
        if (activeFragment != null){
            boolean result = activeFragment.onMyBackPressed();
            if (result){
                return true;
            }
            Fragment parentFragment = activeFragment.getParentFragment();
            if (dispatchBackPressEvent((BaseFragment) parentFragment)){
                return true;
            }
        }
        return false;
    }
}

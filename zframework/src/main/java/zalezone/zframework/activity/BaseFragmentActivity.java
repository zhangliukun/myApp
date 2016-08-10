package zalezone.zframework.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zalezone.zframework.BaseApplication;
import zalezone.zframework.R;
import zalezone.zframework.fragment.BaseFragment;
import zalezone.zframework.util.SharedPreferencesUtil;

/**
 * Created by zale on 16/8/10.
 */
public abstract class BaseFragmentActivity extends FragmentActivity{
    private Bundle mSavedState;

    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseApplication.setTopActivity(this);
        if(isInitInThisActivity()) {
            ((BaseApplication) getApplication()).init();
        }
        int layoutRes = getContainerLayoutId();
        if(layoutRes>0){
            setContentView(layoutRes);
        }else{
            setContentView(R.layout.act_fragment);
        }
        if (savedState == null) {
            this.mSavedState = getIntent().getExtras();
        } else {
            this.mSavedState = savedState;
        }
    }

    public boolean isInitInThisActivity() {
        return true;
    }
    public int getContainerLayoutId(){
        return -1;
    }

    public void setActivityFullScreen() {
		/* set it to be no title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* set it to be full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 给activity状态栏赋背景
     *
     * @return
     */
    public abstract int getStatusBarBgRes();
    /**
     * 为了给签名验证用的
     * object [0] pageNameMd5 包名的MD5
     * object [1] SigInfo 签名的MD5
     * object [2] isRelease 是否是发布版本
     */
    public abstract Object[] getAppInfo();
    /**
     * 获取Intent传过来的参数，用于替代方法getIntent().getExtras() <br>
     * 用于解决Activity重新启动后，原有的Intent中的参数丢失问题，已做容错处理，可以放心调用
     *
     * @return
     */
    public Bundle getIntentExtras() {
        if (mSavedState == null)
            mSavedState = new Bundle();
        return mSavedState;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            String FRAGMENTS_TAG = "android:support:fragments";
            outState.remove(FRAGMENTS_TAG);
        }
        try{
            super.onSaveInstanceState(outState);
        }catch(Exception e){

        }
        if (mSavedState != null)
            outState.putAll(mSavedState);

    }

    public void addFragment(int containerViewId, Fragment fra, String tag) {
        if (fra != null&&!((BaseFragment)fra).isAddFix())
        {
            ((BaseFragment) fra).setIsAdd(true);
            getSupportFragmentManager().beginTransaction()
                    .add(containerViewId, fra, tag).commitAllowingStateLoss();
        }
    }

    public void addFragment(int containerViewId, Fragment fra) {
        if (fra != null&&!((BaseFragment)fra).isAddFix())
        {
            ((BaseFragment) fra).setIsAdd(true);
            getSupportFragmentManager().beginTransaction()
                    .add(containerViewId, fra).commitAllowingStateLoss();
        }
    }

    protected void addFragmentToLayout(int resId, Fragment fra, int inAnim,
                                       int outAnim)
    {
        if (fra != null&&!((BaseFragment)fra).isAddFix())
        {
            ((BaseFragment)fra).setIsAdd(true);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (inAnim != 0 && outAnim != 0)
            {
                ft.setCustomAnimations(inAnim, outAnim, inAnim, outAnim);
            }
            ft.add(resId, fra);
            ft.commitAllowingStateLoss();
        }
    }

    protected void addFragmentToLayout(int resId, Fragment fra)
    {
        addFragmentToLayout(resId, fra, 0, 0);
    }

    protected void showFragment(Fragment fra, int inAnim, int outAnim)
    {

        if (fra == null || !fra.isAdded()) return;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (inAnim != 0 && outAnim != 0)
        {
            ft.setCustomAnimations(inAnim, outAnim, inAnim, outAnim);
        }
        ft.show(fra);
        ft.commitAllowingStateLoss();
    }

    protected void showFragment(Fragment fra)
    {
        showFragment(fra, 0, 0);
    }

    protected void hideFragment(Fragment fra, int inAnim, int outAnim)
    {

        if (fra == null || !fra.isAdded() || fra.isHidden()) return;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (inAnim != 0 && outAnim != 0)
        {
            ft.setCustomAnimations(inAnim, outAnim, inAnim, outAnim);
        }
        ft.hide(fra);
        ft.commitAllowingStateLoss();
    }

    protected void hideFragment(Fragment fra)
    {
        hideFragment(fra, 0, 0);
    }



    public void replaceFragment(int containerViewId, Fragment fragment,
                                String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment, tag).commitAllowingStateLoss();
    }

    public void replaceFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment).commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.act_fragment_container, fragment).commitAllowingStateLoss();
    }


    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.setTopActivity(this);
        onResumeMy();
    }

    public void onResumeMy(){

    }

    @Override
    public void onPause() {
        super.onPause();
        onPauseMy();
    }

    public void onPauseMy(){

    }

    /**
     * 显示当前Activity的Fragment
     */
    public void showFragment() {}

    /**
     * 隐藏当前Activity的Fragment
     */
    public void hideFragment(){}

    protected Intent intent(Class<?> clz) {
        return new Intent(this, clz);
    }

    public void showToastShort(String text) {

        if(Looper.myLooper()!=Looper.getMainLooper())
            return;

        if (TextUtils.isEmpty(text) || this == null) {
            return;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (TextUtils.isEmpty(text) || this == null) {
            return;
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(int textId) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (textId == 0 || this == null) {
            return;
        }
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(int textId) {
        if(Looper.myLooper()!=Looper.getMainLooper())
            return;
        if (textId == 0 || this == null) {
            return;
        }
        Toast.makeText(this, textId, Toast.LENGTH_LONG).show();
    }

    private SharedPreferencesUtil sp;
    private Map<String, Integer> permissions;

    // String 是权限的名称 ,Integer表示没有此权限的提示语
//    @SuppressLint("NewApi")
//    public void checkPermission(Map<String, Integer> permissions) {
//        if(permissions == null || permissions.size() == 0) {
//            return;
//        }
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
//        {
//            return;
//        }
//
//        this.permissions = permissions;
//        sp = SharedPreferencesUtil.getInstance(this);
//
//        List<String> permissionsNeeded = new ArrayList<String>();
//
//        final List<String> permissionsList = new ArrayList<String>();
//
//        for (Map.Entry<String, Integer> permission : permissions.entrySet()) {
//            if (!addPermission(permissionsList, permission.getKey()))
//            {
//                permissionsNeeded.add(permission.getKey());
//            }
//        }
//
//        if (permissionsList.size() > 0)
//        {
//            if (permissionsNeeded.size() > 0 && sp.getBoolean(PreferenceConstantsLib.IS_PERMISSION_MAIN_ASKED, false))
//            {
//                List<Integer> contentList = new ArrayList<Integer>();
//                for (Map.Entry<String, Integer> permission : permissions.entrySet()) {
//                    boolean flag = permissionsNeeded.contains(permission.getKey());
//                    if(flag) {
//                        contentList.add(permissions.get(permission.getKey()));
//                    }
//                }
//                if(contentList.size() != 0) {
//                    showPermissionToast(contentList);
//                }
//            }
//            requestPermissions(
//                    permissionsList.toArray(new String[permissionsList.size()]),
//                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//        }
//    }
//
//    @SuppressLint("NewApi")
//    private boolean addPermission(List<String> permissionsList,
//                                  String permission)
//    {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
//        {
//            permissionsList.add(permission);
//            if (!shouldShowRequestPermissionRationale(permission))
//            {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 123;
//    public final static int REQUEST_CODE_ASK_PERMISSIONS = 345;

//    @SuppressLint("NewApi")
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           final String[] permissions, final int[] grantResults)
//    {
//
//        switch (requestCode)
//        {
//            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
//            {
//                sp.saveBoolean(PreferenceConstantsLib.IS_PERMISSION_MAIN_ASKED, true);
//
//                List<Integer> contentList = new ArrayList<Integer>();
//                if (grantResults != null && grantResults.length > 0
//                        && permissions != null
//                        && permissions.length >= grantResults.length) {
//                    for (int i = 0; i < grantResults.length; i++) {
//                        if (PackageManager.PERMISSION_GRANTED != grantResults[i]
//                                && this.permissions.containsKey(permissions[i])) {
//                            contentList.add(this.permissions.get(permissions[i]));
//                        }
//                    }
//                }
//
//                if(contentList.size() != 0) {
//                    showPermissionToast(contentList);
//                }
//            }
//            break;
//
//            case REQUEST_CODE_ASK_PERMISSIONS:
//            {
//                if (grantResults != null && grantResults.length > 0
//                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    List<Integer> contentList = new ArrayList<Integer>();
//                    contentList.add(R.string.deny_perm_sdcard);
//                    showPermissionToast(contentList);
//                }
//                break;
//            }
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions,
//                        grantResults);
//        }
//    }

//    private void showPermissionToast(List<Integer> contentList)
//    {
//
//        if(contentList==null||contentList.size()==0)
//            return;
//
//        String content = "";
//        for (Integer strId : contentList) {
//            content += (getResources().getString(strId)) + "\r\n";
//        }
//        if(TextUtils.isEmpty(content)) {
//            return;
//        }
//
//        content += getResources().getString(R.string.deny_perm_lastcontent);
//
//        if(dialogBuilder != null) {
//            dialogBuilder.cancle();
//            dialogBuilder = null;
//        }
//
//        dialogBuilder = new DialogBuilder(BaseFragmentActivity.this);
//        dialogBuilder.setMessage(content).setOkBtn("去设置", new DialogBuilder.DialogCallback() {
//            @Override
//            public void onExecute() {
//                try {
//                    BaseUtil.showAppDetails(BaseFragmentActivity.this ,getPackageName());
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).showConfirm();

//		CustomToast.showToast(getApplicationContext(), content, Toast.LENGTH_LONG);
//    }

    //private DialogBuilder dialogBuilder;


    public abstract void removeFramentFromManageFragment(Fragment fra);
}

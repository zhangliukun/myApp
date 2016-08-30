package zalezone.zframework.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import zalezone.zframework.BaseApplication;
import zalezone.zframework.R;
import zalezone.zframework.fragment.BaseFragment;
import zalezone.zframework.fragment.FraManager;

/**
 * Created by zale on 16/8/10.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity{
    private Bundle mSavedState;

    private FraManager mFraManager;

    private int mDefaultFragmentBackground = 0;

    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseApplication.setTopActivity(this);
        if(isInitInThisActivity()) {
            ((BaseApplication) getApplication()).init();
        }

        mFraManager = getFraManager();

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


    public FraManager getFraManager() {
        if (mFraManager == null) {
            mFraManager = new FraManager(this);
        }
        return mFraManager;
    }

    @Override
    public void onBackPressed() {
        BaseFragment activeFragment = mFraManager.getTopActiveFragment(null,getSupportFragmentManager());
        if (mFraManager.dispatchBackPressEvent(activeFragment)){
            return;
        }
        onMyBackPress();

    }

    public void loadRootFragment(int containerId, BaseFragment toFragment) {
        mFraManager.loadRootTransaction(getSupportFragmentManager(), containerId, toFragment);
    }

    public void replaceLoadRootFragment(int containerId, BaseFragment toFragment, boolean addToBack){
        mFraManager.replaceTransaction(getSupportFragmentManager(),containerId,toFragment,addToBack);
    }

    public void showHideFragment(BaseFragment showFragment, BaseFragment hideFragment) {
        mFraManager.showHideFragment(getSupportFragmentManager(), showFragment, hideFragment);
    }
    public void start(BaseFragment toFragment) {
        mFraManager.dispatchStartTransaction(getSupportFragmentManager(),getTopFragment(),toFragment,FraManager.TYPE_ADD,null);
    }

    public BaseFragment getTopFragment() {
        return mFraManager.getTopFragment(getSupportFragmentManager());
    }

    public <T extends BaseFragment> T findFragment(Class<T> fragmentClass){
        return mFraManager.findStackFragment(fragmentClass,getSupportFragmentManager());
    }


    public void pop(){
        mFraManager.back(getSupportFragmentManager());
    }



    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    public void onMyBackPress() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finish();
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

    public int getmDefaultFragmentBackground() {
        return mDefaultFragmentBackground;
    }

    public void setmDefaultFragmentBackground(int mDefaultFragmentBackground) {
        this.mDefaultFragmentBackground = mDefaultFragmentBackground;
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
}

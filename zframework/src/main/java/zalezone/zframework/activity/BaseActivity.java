package zalezone.zframework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import zalezone.zframework.BaseApplication;
import zalezone.zframework.R;

/**
 * Created by zale on 16/8/10.
 */
@Deprecated
public abstract class BaseActivity extends Activity {
    private Bundle mSavedState;

    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        BaseApplication.setTopActivity(this);
        ((BaseApplication)getApplication()).init();
        setContentView(R.layout.act_fragment);
        if (savedState == null) {
            this.mSavedState = getIntent().getExtras();
        } else {
            this.mSavedState = savedState;
        }
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
     * @return
     */
    public abstract int getStatusBarBgRes();

    /**
     * 为了给签名验证用的
     * object [0] pageNameMd5 包名的MD5
     * object [1] SigInfo 签名的MD5
     * object [2] isRelease 是否是发布版本
     * object [3] isCheckPkg 是否验证包名
     * object [4] isCheckSign 是否验证签名
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
        super.onSaveInstanceState(outState);
        if (mSavedState != null)
            outState.putAll(mSavedState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.setTopActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void showToastShort(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(int textId) {
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(int textId) {
        Toast.makeText(this, textId, Toast.LENGTH_LONG).show();
    }

}

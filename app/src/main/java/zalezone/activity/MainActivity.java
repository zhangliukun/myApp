package zalezone.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import zalezone.surfaceview.R;
import zalezone.ui.fragment.FirstFragment;
import zalezone.zframework.activity.BaseFragmentActivity;
import zalezone.zframework.fragment.BaseFragment;
import zalezone.zframework.fragment.BaseLazyMainFragment;
import zalezone.zframework.fragment.FraManager;

/**
 * Created by zale on 16/8/12.
 */
public class MainActivity extends BaseFragmentActivity implements BaseLazyMainFragment.OnBackToFirstListener{

    private BaseFragment[] mFragments = new BaseFragment[4];


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.act_fragment);

        if (savedState == null){
            mFragments[0] = FirstFragment.newInstance(0);
            loadRootFragment(R.id.base_container,mFragments[0]);
        }else {
            mFragments[0] = findFragment(FirstFragment.class);
        }
    }

    @Override
    public int getStatusBarBgRes() {
        return 0;
    }

    @Override
    public Object[] getAppInfo() {
        return new Object[0];
    }


    @Override
    public void onBackToFirstFragment() {

    }
}

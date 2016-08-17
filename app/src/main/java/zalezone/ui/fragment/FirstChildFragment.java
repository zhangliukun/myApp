package zalezone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseFragment;
import zalezone.zframework.fragment.BaseLazyMainFragment;

/**
 * Created by zale on 16/8/17.
 */
public class FirstChildFragment extends BaseFragment{

    private Button btn1;
    private Button btn2;
    private int mCount;

    public static FirstChildFragment newInstance(int count){
        Bundle args = new Bundle();
        FirstChildFragment fragment = new FirstChildFragment();
        args.putInt("count",count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        if (getArguments()!=null){
            mCount = getArguments().getInt("count");
        }
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn1.setText("直接add创建le"+getFragmentManager().getBackStackEntryCount());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(FirstChildFragment.newInstance(++mCount));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.replaceLoadRootFragment(getContainerId(),FirstChild2Fragment.newInstance(0),true);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected View getLoadingView() {
        return null;
    }

    @Override
    protected View getNetworkErrorView() {
        return null;
    }

    @Override
    protected View getNoContentView() {
        return null;
    }

    @Override
    public int getContainerLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    public boolean onMyBackPressed() {
        pop();
        return true;
    }
}

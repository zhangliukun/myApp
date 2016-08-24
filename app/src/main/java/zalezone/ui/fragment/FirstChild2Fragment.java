package zalezone.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseFragment;

/**
 * Created by zale on 16/8/17.
 */
public class FirstChild2Fragment extends BaseFragment{

    private Button btn1;
    private Button btn2;
    private int mCount;

    public static FirstChild2Fragment newInstance(int count){
        Bundle args = new Bundle();
        FirstChild2Fragment fragment = new FirstChild2Fragment();
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
        btn1.setText("直接add创建le"+mCount);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mCount;
                start(FirstChildFragment.newInstance(mCount));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mCount;
                mActivity.replaceLoadRootFragment(getContainerId(),FirstChild2Fragment.newInstance(mCount),false);
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
        return pop();
    }
}

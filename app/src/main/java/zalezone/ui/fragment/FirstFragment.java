package zalezone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.zip.Inflater;

import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseLazyMainFragment;

/**
 * Created by zale on 16/8/17.
 */
public class FirstFragment extends BaseLazyMainFragment{

    private Button btn1;
    private int mCount;

    public static FirstFragment newInstance(int count){
        Bundle args = new Bundle();
        FirstFragment fragment = new FirstFragment();
        args.putInt("count",count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null){

        }
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        if (getArguments()!=null){
            mCount = getArguments().getInt("count");
        }
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setText("First");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(FirstChildFragment.newInstance(1));
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
}

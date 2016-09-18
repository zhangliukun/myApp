package zalezone.view.guide;

import android.os.Bundle;
import android.view.View;

import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseFragment;

/**
 * Created by zale on 16/9/18.
 */
public class CommonGuideFragment extends BaseFragment{

    GuideView guideView;

    @Override
    protected void initUi(Bundle savedInstanceState) {
        guideView = new GuideView(mActivity);
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
        return R.layout.fra_guide;
    }
}

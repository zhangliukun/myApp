package zalezone.view.guide;

import android.os.Bundle;
import android.view.View;

import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseFragment;

/**
 * Created by zale on 16/9/18.
 */
public class CommonGuideFragment extends BaseFragment{

    private GuideViewLayout guideViewLayout;

    @Override
    protected void initUi(Bundle savedInstanceState) {
        guideViewLayout = (GuideViewLayout) findViewById(R.id.guide_view_layout);
    }

    @Override
    protected void loadData() {
        guideViewLayout.addGuideOperation(new GuideModel(400,400,100, GuideMask.TYPE_GUIDE_CIRCLE,R.drawable.guide_download,100,500,40,0));
        guideViewLayout.addGuideOperation(new GuideModel(800,800,200, GuideMask.TYPE_GUIDE_CIRCLE,R.drawable.guide_square,300,100,40,0));
        guideViewLayout.startGuide(new GuideViewLayout.OnGuideFinishedListener() {
            @Override
            public void guideFinished() {
                getActivity().onBackPressed();
            }
        });
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

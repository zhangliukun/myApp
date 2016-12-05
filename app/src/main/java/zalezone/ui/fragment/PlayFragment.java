package zalezone.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import zalezone.aidlstudy.ZPlayerInterface;
import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseFragment;

/**
 * Created by zale on 16/9/9.
 */
public class PlayFragment extends BaseFragment{


    private ImageButton playControlBtn;
    private SeekBar playProgressBar;

    private ZPlayerInterface mStub;

    @Override
    protected void initUi(Bundle savedInstanceState) {
        playControlBtn = (ImageButton) findViewById(R.id.play_btn);
        playProgressBar = (SeekBar) findViewById(R.id.play_progress);
    }

    @Override
    protected void loadData() {
//        if (mActivity!=null&&mActivity instanceof MainActivity){
//            mStub = ((MainActivity) mActivity).getPlayerBinder();
//        }
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
        return R.layout.fragment_player;
    }
}

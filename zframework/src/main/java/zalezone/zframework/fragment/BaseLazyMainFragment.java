package zalezone.zframework.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by zale on 16/8/17.
 */
public abstract class BaseLazyMainFragment extends BaseFragment{

    private boolean mInited = false;
    protected OnBackToFirstListener mOnBackToFirstListener;
    private Bundle mSavedInstanceState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener){
            mOnBackToFirstListener = (OnBackToFirstListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            if (!isHidden()){
                mInited = true;
                initLazyView(null);
            }
        }else {
            //仅在saveInstanceState!=null时有意义,是库帮助记录Fragment状态的方
            if (!isMyHidden()){
                mInited = true;
                initLazyView(savedInstanceState);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnBackToFirstListener = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!mInited && !hidden){
            mInited = true;
            initLazyView(mSavedInstanceState);
        }
    }

    @Override
    public boolean onMyBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount()>1){
            popChild();
        }else {
            mActivity.finish();
        }
        return true;
    }

    /**
     * 懒加载
     */
    protected abstract void initLazyView(@Nullable Bundle savedInstanceState);

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}

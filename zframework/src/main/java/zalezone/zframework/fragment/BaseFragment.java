package zalezone.zframework.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashSet;
import java.util.Set;

import zalezone.zframework.activity.BaseFragmentActivity;

/**
 * Created by zale on 16/8/10.
 */
public abstract class BaseFragment extends Fragment{
    public View mContainerView;

    private View mLoadingView;
    private View mNetworkErrorView;
    private View mNoContentView;

    private Set<ImageView> mImageViewList = new HashSet<ImageView>();
    //protected Context mContext;

    protected BaseFragmentActivity mActivity;
    private RelativeLayout.LayoutParams lp;
    private LoadCompleteType loadCompleteType;

    private int mContainerId;   // 该Fragment所处的Container的id
    private boolean mIsRoot;
    private boolean mIsHidden = true;//记录fragment的hide和show的状态
    private InputMethodManager mIMM;
    private boolean mNeedHideSoft;//隐藏软键盘

    private FraManager mFraManager;

    protected abstract void initUi(Bundle savedInstanceState);

    protected abstract void loadData();


    @Override
    public void onResume() {
            super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseFragmentActivity) activity;
        mFraManager = mActivity.getFraManager();
    }

    public boolean isViewContained = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle !=null){
            mIsRoot = bundle.getBoolean(FraManager.ARG_IS_ROOT,false);
            mContainerId = bundle.getInt(FraManager.FRAGMENTATION_ARG_CONTAINER);
        }

        if (savedInstanceState == null){

        }else {
            mIsHidden = savedInstanceState.getBoolean(FraManager.FRAGMENTATION_STATE_SAVE_IS_HIDDEN);
        }
        //内存重启解决fragment重叠问题
        processRestoreInstanceState(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        ViewGroup.LayoutParams vgLp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mLoadingView = getLoadingView();
        mNetworkErrorView = getNetworkErrorView();
        mNoContentView = getNoContentView();


        if (mLoadingView != null || mNetworkErrorView != null
                || mNoContentView != null) {
            RelativeLayout parentContainer = new RelativeLayout(
                    getActivity());
            mContainerView = inflater.inflate(getContainerLayoutId(),
                    parentContainer, true);
            isViewContained = true;
        } else {
            isViewContained = false;
            mContainerView = inflater.inflate(getContainerLayoutId(),
                    container, false);
            return mContainerView;
        }
        mContainerView.setLayoutParams(vgLp);
        mContainerView.setClickable(true);
        return mContainerView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi(savedInstanceState);
        loadData();

    }

    protected abstract View getLoadingView();

    protected abstract View getNetworkErrorView();

    protected abstract View getNoContentView();


    public int getContainerId() {
        return mContainerId;
    }

    public View findViewById(int id){
        View view = null;
        if (mContainerView!=null){
            view = mContainerView.findViewById(id);
        }else {
            view = getActivity().findViewById(id);
        }
        return view;
    }

    /**
     * 获取item布局文件id
     *
     * @return
     */
    public abstract int getContainerLayoutId();

    public void loadRootFragment(int containerId, BaseFragment toFragment) {
        mFraManager.loadRootTransaction(getChildFragmentManager(), containerId, toFragment);
    }

    public void pop(){
        mFraManager.back(getFragmentManager());
    }

    public void popChild(){
        mFraManager.back(getChildFragmentManager());
    }

    public void replaceLoadRootFragment(int containerId, BaseFragment toFragment, boolean addToBack){
        mFraManager.replaceTransaction(getChildFragmentManager(),containerId,toFragment,addToBack);
    }

    public void showHideFragment(BaseFragment showFragment, BaseFragment hideFragment) {
        mFraManager.showHideFragment(getChildFragmentManager(), showFragment, hideFragment);
    }
    public void start(BaseFragment toFragment) {
        mFraManager.dispatchStartTransaction(getFragmentManager(),getTopFragment(),toFragment,FraManager.TYPE_ADD,null);
    }

    public BaseFragment getTopFragment() {
        return mFraManager.getTopFragment(getFragmentManager());
    }

    public <T extends BaseFragment> T findFragment(Class<T> fragmentClass){
        return mFraManager.findStackFragment(fragmentClass,getFragmentManager());
    }


    private void processRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isMyHidden()) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    public boolean isMyHidden(){
        return mIsHidden;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mNetworkErrorView!=null){
            mNetworkErrorView.setOnClickListener(null);
        }
        if(mNoContentView!=null){
            mNoContentView.setOnClickListener(null);
        }
        mImageViewList.clear();
    }

    public enum LoadCompleteType {
        OK, NETWOEKERROR, NOCONTENT, LOADING
    }

    public View getCreateNoContentView() {
        return mNoContentView;
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    public boolean onMyBackPressed() {
        return false;
    }

}

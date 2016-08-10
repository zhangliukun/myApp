package zalezone.zframework.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import zalezone.zframework.BaseApplication;
import zalezone.zframework.R;
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
    protected Context mContext;

    protected Activity mActivity;
    private RelativeLayout.LayoutParams lp;
    private LoadCompleteType loadCompleteType;

    public BaseFragment() {
        super();
        mContext = BaseApplication.getMyApplicationContext();
    }

    @Override
    public void onResume() {
        if(isAdded()){
            super.onResume();
            restoreImageViewBitmap();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        recycleImageViewBitmap();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

//    public void checkPermission(Map<String, Integer> permissions) {
//        if(getActivity()!=null && getActivity() instanceof BaseFragmentActivity)
//            ((BaseFragmentActivity)getActivity()).checkPermission(permissions);
//    }

    public boolean isViewContained = false;

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

    protected abstract View getLoadingView();

    protected abstract View getNetworkErrorView();

    protected abstract View getNoContentView();


    /**
     * 获取item布局文件id
     *
     * @return
     */
    public abstract int getContainerLayoutId();


    /**
     * 重新获取ImageView
     */
    public void restoreImageViewBitmap() {

        Iterator<ImageView> iterator = mImageViewList.iterator();

        while (iterator.hasNext()){
            ImageView img = iterator.next();
            if(img.getWindowToken() != null) {
                //说明该view还在布局上面
//                String url = (String) img.getTag(R.id.tag_first);
//                int drawableId = (Integer) img.getTag(R.id.tag_second);
//                ImageManager.from(getActivity()).displayImage(img, url, drawableId);
//                Object thirdTag = img.getTag(R.id.tag_third);
//                if(thirdTag!=null){
//                    img.setBackgroundResource((Integer)thirdTag);
//                }
            }else{
                //view已经不在布局上面直接扔去
                iterator.remove();
            }
        }

    }

    /**
     * 回收
     */
    public void recycleImageViewBitmap() {
        for (ImageView img : mImageViewList) {
            if(img!=null) {
                img.setBackgroundResource(0);
                img.setImageResource(0);
            }
        }
    }

    protected Intent intent(Class<?> clz) {
        Intent intent = new Intent(getActivity(), clz);
        intent.putExtra("from_activity", getActivity().getComponentName()
                .getClassName());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    public void startActivityClass(Class<?> clz) {
        startActivity(intent(clz));
    }

    public void showToastShort(String text) {
        if (!TextUtils.isEmpty(text) && mActivity != null) {
            Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToastLong(String text) {
        if (!TextUtils.isEmpty(text) && mActivity != null) {
            Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
        }
    }

    public void showToastShort(int textId) {
        if (textId > 0 && mActivity != null) {
            Toast.makeText(mActivity, textId, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToastLong(int textId) {
        if (textId > 0 && mActivity != null) {
            Toast.makeText(mActivity, textId, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 异步请求结束之后是否可以更新ui
     *
     * @return
     */
    public boolean canUpdateUi() {
        if (!isAdded() || isRemoving() || isDetached()) {
            return false;
        }
        return true;
    }

    public boolean onBackPressed() {

        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            getChildFragmentManager().popBackStack(
                    getChildFragmentManager()
                            .getBackStackEntryAt(
                                    getChildFragmentManager()
                                            .getBackStackEntryCount() - 1)
                            .getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        }

        return false;

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
        recycleImageViewBitmap();
        mImageViewList.clear();
    }

    public enum LoadCompleteType {
        OK, NETWOEKERROR, NOCONTENT, LOADING
    }

    private boolean isAdd = false;

    public void setIsAdd(boolean isAdd){
        this.isAdd = isAdd;
    }

    /**
     * 外面不要轻易调用此方法，尽量调用系统原生方法isAdded()
     * @return
     */
    public boolean isAddFix(){
        return isAdd||isAdded();
    }

    public View getCreateNoContentView() {
        return mNoContentView;
    }



}

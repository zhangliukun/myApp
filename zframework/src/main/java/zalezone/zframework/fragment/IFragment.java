package zalezone.zframework.fragment;

/**
 * Created by zale on 16/8/16.
 */
public interface IFragment {

    void loadRootFragment(int containerId,BaseFragment toFragment);

    void replaceLoadRootFragment(int containerId,BaseFragment toFragment,boolean addToBack);

    void showHideFragment(BaseFragment showFragment,BaseFragment hideFragment);

    void startFragment(BaseFragment toFragment);

    void replaceFragment(BaseFragment toFragment,boolean addToBack);

    void startWithPop(BaseFragment toFragment);

    BaseFragment getTopFragment();

    BaseFragment getPreFragment();

    <T extends BaseFragment> T findFragment(Class<T> fragmentClass);

    void pop();
}

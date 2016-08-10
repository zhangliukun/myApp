package zalezone.zframework;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by zale on 16/8/10.
 */
public abstract class BaseApplication extends Application{

    public static BaseApplication mAppInstance;
    protected boolean hasInit = false;
    private static Activity mTopActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
    }

    public abstract void initApp();

    /**
     *
     * 退出应用耗时操作，可以重载该方法
     *
     */
    public void exitAppInThread(){

    }

    public void init(){
        if(hasInit) return;
        hasInit = true;
        initApp();
    }

    public void exitApp(){
        hasInit = false;
        mTopActivity = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                exitAppInThread();
            }
        }).start();
    }


    public static  Context getMyApplicationContext(){
        if(isTopActivityAvaliable()){
            return mTopActivity.getApplicationContext();
        }else{
            return mAppInstance;
        }
    }

    public static boolean isTopActivityAvaliable(){
        return (null != mTopActivity && !mTopActivity.isFinishing());
    }

    public static Activity getTopActivity() {
        return (mTopActivity == null || mTopActivity.isFinishing())?null:mTopActivity;
    }

    public static void setTopActivity(Activity topActivity) {
        mTopActivity = topActivity;
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    @TargetApi(14)
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}

package zalezone.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zalezone.adapter.MenuItemAdapter;
import zalezone.aidlstudy.IZaleInterface;
import zalezone.aidlstudy.MyService;
import zalezone.aidlstudy.Zale;
import zalezone.aidlstudy.ZaleBinder;
import zalezone.model.menu.MenuItem;
import zalezone.surfaceview.R;
import zalezone.ui.fragment.FirstChildFragment;
import zalezone.zframework.activity.BaseFragmentActivity;

/**
 * Created by zale on 16/8/12.
 * main
 */
public class MainActivity extends BaseFragmentActivity{


    private List<MenuItem> mList = new ArrayList<>();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    private MenuItemAdapter menuItemAdapter;
    private View headerView;
    private SimpleDraweeView headerImage;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.act_main);
        startPlayerService();
        initUI();

    }

    private void initUI() {
        findViews();
        initToolBar();
        initMenu();
        initMain();
        setStatuBarTranslucentStatus();
    }

    private void initMain() {
        loadRootFragment(R.id.base_container, FirstChildFragment.newInstance(0));
    }

    private void initHeaderView(){
        headerView = View.inflate(this,R.layout.drawer_listview_header,null);
        headerImage = (SimpleDraweeView) headerView.findViewById(R.id.header_image);
        headerImage.setImageURI("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/05/0F/ChMkJ1erCYqIQptxAAPESMfBQZoAAUU6QB4oVwAA8Rg091.jpg");
    }

    private void initMenu() {
        initHeaderView();
        if (mList==null)mList = new ArrayList<>();
        mList.addAll(Arrays.asList(new MenuItem("音乐"),
                new MenuItem("电影"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("小说"),
                new MenuItem("音乐")));
        menuItemAdapter = new MenuItemAdapter(this,mList);
        mDrawerList.addHeaderView(headerView);
        mDrawerList.setAdapter(menuItemAdapter);
        mDrawerList.setOnItemClickListener(listener);
    }

    private void initToolBar() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setPadding(toolbar.getPaddingLeft(),getResources().getDimensionPixelSize(R.dimen.status_bar_height),toolbar.getPaddingRight(),toolbar.getPaddingBottom());
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    private DrawerItemClickListener listener = new DrawerItemClickListener();

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showToastShort(id+"");
        }
    }


    private void setStatuBarTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            int flag = getWindow().getDecorView().getSystemUiVisibility();
            getWindow().getDecorView().setSystemUiVisibility(
                    flag | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow()
                    .addFlags(
                            WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else if (Build.VERSION.SDK_INT < 21 && Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void startPlayerService(){
        Intent intent = new Intent();
        intent.setAction("com.zalezone.service.REMOTE_SERVICE");
        intent.setPackage("zalezone.aidlstudy.MyService");
        startService(MyService.getIntent(getApplicationContext()));
        getApplicationContext().bindService(MyService.getIntent(getApplicationContext()),serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onMyBackPress() {
        super.onMyBackPress();
    }

    private IZaleInterface mStub;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mStub = ZaleBinder.asInterface(service);
            Log.i("player_service","service connected");
            try {
                mStub.sayHello("hello");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Zale zale = new Zale();
            zale.setName("zale");
            zale.setAge(20);
            try {
                mStub.introduceMe(zale);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("player_service","service disconnected");
            startPlayerService();
        }
    };

    public IZaleInterface getPlayerBinder(){
        if (mStub == null){
            startPlayerService();
        }
        return mStub;
    }

}

package zalezone.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import zalezone.surfaceview.R;
import zalezone.zframework.activity.BaseFragmentActivity;
import zalezone.zframework.fragment.BaseLazyMainFragment;

/**
 * Created by zale on 16/8/12.
 */
public class MainActivity extends BaseFragmentActivity implements BaseLazyMainFragment.OnBackToFirstListener{


    private String[] mPlantTitles;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.act_main);
        initUI();

    }

    private void initUI() {
        findViews();
        initToolBar();
        initMenu();
    }


    private void initMenu() {
        mPlantTitles = new String[]{"1","2","3","4","5"};
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,R.id.menu_item,mPlantTitles));
        mDrawerList.setOnItemClickListener(listener);
    }

    private void initToolBar() {
        toolbar.setTitle("ZALE");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
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


    @Override
    public int getStatusBarBgRes() {
        return 0;
    }

    @Override
    public Object[] getAppInfo() {
        return new Object[0];
    }


    @Override
    public void onBackToFirstFragment() {

    }

    @Override
    public void onMyBackPress() {
        super.onMyBackPress();
    }
}

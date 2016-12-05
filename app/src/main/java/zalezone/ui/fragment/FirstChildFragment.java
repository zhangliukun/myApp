package zalezone.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zalezone.surfaceview.R;
import zalezone.zframework.fragment.BaseFragment;

/**
 * Created by zale on 16/8/17.
 */
public class FirstChildFragment extends BaseFragment{

    private Button btn1;
    private Button btn2;
    private int mCount;
    private EditText emojText;

    public static FirstChildFragment newInstance(int count){
        Bundle args = new Bundle();
        FirstChildFragment fragment = new FirstChildFragment();
        args.putInt("count",count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        if (getArguments()!=null){
            mCount = getArguments().getInt("count");
        }
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn1.setText("直接add创建le"+mCount);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mCount;
                start(FirstChildFragment.newInstance(mCount));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mCount;
                mActivity.replaceLoadRootFragment(getContainerId(),FirstChild2Fragment.newInstance(mCount),false);
            }
        });

        emojText = (EditText) findViewById(R.id.emoj_text);
        emojText.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i("edittext",unicode2String(s.toString()));
        }
    };

    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
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
        return R.layout.fragment_first;
    }

    @Override
    public boolean onMyBackPressed() {
        return pop();
    }


}

package zalezone.zframework.adapter;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zale on 16/8/29.
 */
public class AbstractAdapter<T> extends BaseAdapter{

    protected Context mContext;

    protected List<T> mListData;

    protected LayoutInflater layoutInflater;

    public AbstractAdapter(Context context,List<T> listData) {
        this.mContext = context;
        this.mListData = listData;
        layoutInflater = layoutInflater.from(context);
    }

    public boolean containItem(T item){

        if(mListData==null)
            return false;
        else{
            return mListData.contains(item);
        }

    }

    public List<T> getListData() {
        return mListData;
    }

    public void setListData(List<T> data) {
        this.mListData = data;
    }

    public void addListData(List<T> data){
        if(mListData==null){
            mListData = data;
        }else{
            mListData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addListDataWithoutNotify(List<T> data){
        if(mListData==null){
            mListData = data;
        }else{
            mListData.addAll(data);
        }
    }

    public void deleteListData(int position) {
        if(mListData!=null&&mListData.size()>position){
            mListData.remove(position);
            notifyDataSetChanged();
        }
    }

    public void deleteListData(T data) {
        if(mListData!=null){
            mListData.remove(data);
            notifyDataSetChanged();
        }
    }

    public void deleteListDatas(List<T> data) {
        if(mListData!=null){
            mListData.removeAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if(mListData!=null){
            mListData.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (mListData!=null){
            return mListData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mListData!=null&&mListData.size()>0&&position<mListData.size()){
            return mListData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void notifyDataSetInvalidated() {
        if(!isInMainLooper("notifyDataSetInvalidated")){
            return;
        }
        super.notifyDataSetInvalidated();
    }

    private boolean isInMainLooper(String name){
        if(Looper.myLooper()!=Looper.getMainLooper()){
            Log.e("ERROR", "不能在线程中调用"+name+"方法"+getClass().getName());
            return false;
        }
        return true;
    }
}

package zalezone.zframework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zale on 16/8/29.
 */
public abstract class HolderAdapter<T> extends AbstractAdapter<T> {

    public HolderAdapter(Context context, List<T> listData) {
        super(context, listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(getConvertViewId(),null);
            holder = buildHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (BaseViewHolder) convertView.getTag();
        }
        bindViewDatas(holder,mListData.get(position),position);
        return convertView;
    }

    public static class BaseViewHolder{

    }

    public abstract int getConvertViewId();

    public abstract BaseViewHolder buildHolder(View convertView);

    public abstract void bindViewDatas(BaseViewHolder h,T t,int position);
}

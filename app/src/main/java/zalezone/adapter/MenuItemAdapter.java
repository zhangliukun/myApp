package zalezone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import zalezone.model.menu.MenuItem;
import zalezone.surfaceview.R;
import zalezone.zframework.adapter.HolderAdapter;

/**
 * Created by zale on 16/8/29.
 */
public class MenuItemAdapter extends HolderAdapter<MenuItem> {

    public MenuItemAdapter(Context context, List<MenuItem> listData) {
        super(context, listData);
    }

    @Override
    public int getConvertViewId() {
        return R.layout.listitem_menu;
    }

    @Override
    public BaseViewHolder buildHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.menuTv = (TextView) convertView.findViewById(R.id.menu_tv);
        return holder;
    }

    @Override
    public void bindViewDatas(BaseViewHolder h, MenuItem menuItem, int position) {
        ViewHolder holder = (ViewHolder) h;
        holder.menuTv.setText(String.format("%s", menuItem.menuText));
    }

    class ViewHolder extends BaseViewHolder{
        public TextView menuTv;
    }
}

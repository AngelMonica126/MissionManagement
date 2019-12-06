package cn.monica.missionimpossible.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.RecordDatabase;
import cn.monica.missionimpossible.bean.ViewDatabase;
import cn.monica.missionimpossible.engine.LockDialogHelper;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.engine.ViewManager;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import cn.monica.missionimpossible.util.ToastUtil;
import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by dream on 2018/7/4.
 */

public class ViewAdapter extends BaseAdapter {
    List<ViewDatabase> viewDatabases;//已经排好序了
    private Context context;
    public ViewAdapter(Context context) {
        this.context = context;
        this.viewDatabases = ViewManager.getInstance().getViews();
    }

    @Override
    public int getCount() {
        return viewDatabases.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.view_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_tv = (TextView) view.findViewById(R.id.item_tv);
            viewHolder.item_delete = (ImageButton) view.findViewById(R.id.item_delete);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.item_tv.setText(viewDatabases.get(position).getTitle());
        viewHolder.item_delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final ViewDatabase viewDatabase = viewDatabases.get(position);
                LockDialogHelper.getInstance().createUnLockDialog(new LockDialogHelper.UnLockListener() {
                    @Override
                    public void onSuccess() {
                        viewDatabases.remove(viewDatabase);
                        viewDatabase.delete();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {
                        ToastUtil.makeToast(context,"密码错误！");
                    }

                    @Override
                    public void onCancel() {
                        ToastUtil.makeToast(context,"取消！");
                    }
                });
                return true;
            }
        });
        return view;
    }

    class ViewHolder {
        TextView item_tv;
        ImageButton item_delete;
    }

}
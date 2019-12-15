package cn.monica.missionimpossible.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.RecordDatabase;
import cn.monica.missionimpossible.engine.LockDialogHelper;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.myinterface.OnRecordExpandableReplaceFragment;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import cn.monica.missionimpossible.util.ToastUtil;
import co.lujun.androidtagview.TagContainerLayout;

public class RecordExpandableAdapter extends BaseExpandableListAdapter {
    private OnRecordExpandableReplaceFragment onRecordExpandableReplaceFragment;
    private List<RecordDatabase>recordDatabases;
    private Context context;
    private String steps[] = {"未开始","已开始","已完成"};
    public RecordExpandableAdapter(Context mcotext, OnRecordExpandableReplaceFragment onRecordExpandableReplaceFragment) {
        this.context =mcotext;
        this.onRecordExpandableReplaceFragment = onRecordExpandableReplaceFragment;
        recordDatabases = RecordManager.getInstance().getRecordDatabases();
    }

    // 获取分组的个数
    @Override
    public int getGroupCount() {
        return recordDatabases.size();
    }
    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    //获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return recordDatabases.get(groupPosition);
    }

    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return recordDatabases.get(groupPosition);
    }

    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     */
// 获取显示指定分组的视图
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.record_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_tv = (TextView) view.findViewById(R.id.item_tv);
            viewHolder.item_tag = (TagContainerLayout) view.findViewById(R.id.item_tag);
            viewHolder.item_delete = (ImageButton) view.findViewById(R.id.item_delete);
            viewHolder.item_createDay = (TextView) view.findViewById(R.id.item_createDay);
            viewHolder.record_item_parent = (LinearLayout) view.findViewById(R.id.record_item_parent);
            viewHolder.item_image = (ImageView) view.findViewById(R.id.record_item_image)   ;
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.item_tag.removeAllTags();
        }
        viewHolder.item_tv.setText(recordDatabases.get(groupPosition).getTitle());
        int step = recordDatabases.get(groupPosition).getStep();
        switch (step)
        {
            case 0:
                viewHolder.record_item_parent.setBackgroundResource(R.drawable.wait_shape);
                break;
            case 1:
                viewHolder.record_item_parent.setBackgroundResource(R.drawable.ongoing_shape);
                break;
            case 2:
                viewHolder.record_item_parent.setBackgroundResource(R.drawable.finish_shape);
                break;
        }
        viewHolder.item_createDay.setText(steps[step]);
        String name = recordDatabases.get(groupPosition).getName();
        File file = new File(context.getFilesDir(), name + ContentValueUtil.TAG);
        String res = FileUtil.readFile(file);
        if (!TextUtils.isEmpty(res)) {
            String[] stings = res.split(ContentValueUtil.DIVIDE);
            for (String s : stings) {
                if (!TextUtils.isEmpty(s)) {
                    viewHolder.item_tag.addTag(s);
                }
            }
        }
        viewHolder.item_delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
                LockDialogHelper.getInstance().createUnLockDialog(new LockDialogHelper.UnLockListener() {
                    @Override
                    public void onSuccess() {
                        recordDatabases.remove(recordDatabase);
                        recordDatabase.delete();
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
        viewHolder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecordExpandableReplaceFragment.onClick(recordDatabases.get(groupPosition));
            }
        });
        return view;
    }

    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */

    //取得显示给定分组给定子位置的数据用的视图

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        LinearLayout   record_item_parent;
        TextView item_tv;
        TagContainerLayout item_tag;
        ImageButton item_delete;
        TextView item_createDay;
        ImageView item_image;
    }
}

package cn.monica.missionimpossible.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.monica.missionimpossible.R;

public class RecordExpandableAdapter extends BaseExpandableListAdapter {
    private String[] groups;
    private String[][] childs;
    Context mcotext;
    public RecordExpandableAdapter(Context mcotext, String[] groups, String[][] childs) {
        this.mcotext=mcotext;
        this.groups=groups;
        this.childs=childs;
    }

    // 获取分组的个数
    @Override
    public int getGroupCount() {
        return groups.length;
    }
    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childs[groupPosition].length;
    }

    //获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs[groupPosition][childPosition];
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(mcotext).inflate(R.layout.group_layout,null);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv);
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(groups[groupPosition]);
        return convertView;
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
        if(convertView==null){
            convertView= LayoutInflater.from(mcotext).inflate(R.layout.childs_layout,null);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv);
            holder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(childs[groupPosition][childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    ViewHolder holder;
    class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}

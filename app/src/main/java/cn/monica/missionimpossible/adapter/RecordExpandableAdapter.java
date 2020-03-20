package cn.monica.missionimpossible.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.LockDialogHelper;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.myinterface.OnFinishDeleteRecord;
import cn.monica.missionimpossible.myinterface.OnRecordExpandableReplaceFragment;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import cn.monica.missionimpossible.util.ToastUtil;
import cn.monica.missionimpossible.view.MyRatingBar;
import co.lujun.androidtagview.TagContainerLayout;

public class RecordExpandableAdapter extends BaseExpandableListAdapter {
    private OnRecordExpandableReplaceFragment onRecordExpandableReplaceFragment;
    private List<RecordDatabase>recordDatabases;
    private Context context;
    private String steps[] = {"未开始","已开始","已完成"};
    private int id[] = {R.id.record_chile_item_email,R.id.record_chile_item_alarm,R.id.record_chile_item_no};
    public RecordExpandableAdapter(Context mcotext, OnRecordExpandableReplaceFragment onRecordExpandableReplaceFragment) {
        this.context =mcotext;
        this.onRecordExpandableReplaceFragment = onRecordExpandableReplaceFragment;
        recordDatabases = RecordManager.getInstance().getAllRecordDatabases();
    }
    public void Update()
    {
        recordDatabases = RecordManager.getInstance().getAllRecordDatabases();
        this.notifyDataSetChanged();
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
        return groupPosition;
    }


    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
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
        View view = LayoutInflater.from(context).inflate(R.layout.record_item, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.item_tv = (TextView) view.findViewById(R.id.item_tv);
        viewHolder.item_rating_bar = (MyRatingBar) view.findViewById(R.id.record_item_rating_bar);
        viewHolder.item_delete = (ImageButton) view.findViewById(R.id.item_delete);
        viewHolder.item_createDay = (TextView) view.findViewById(R.id.item_createDay);
        viewHolder.record_item_parent = (LinearLayout) view.findViewById(R.id.record_item_parent);
        viewHolder.item_image = (ImageView) view.findViewById(R.id.record_item_image)   ;
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
        viewHolder.item_rating_bar.setStar(recordDatabases.get(groupPosition).getPriority());
        viewHolder.item_rating_bar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
            @Override
            public void onRatingChange(float RatingCount) {
                recordDatabase.setPriority((int) RatingCount);
                RecordManager.getInstance().Add(recordDatabase);
            }
        });
        viewHolder.item_createDay.setText(steps[step]);
        viewHolder.item_delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
                LockDialogHelper.getInstance().createUnLockDialog(new LockDialogHelper.UnLockListener() {
                    @Override
                    public void onSuccess() {
                        RecordManager.getInstance().Delete(recordDatabase, new OnFinishDeleteRecord() {
                            @Override
                            public void onFinish() {
                                notifyDataSetChanged();
                            }
                        });
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
            RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
            @Override
            public void onClick(View view) {
                onRecordExpandableReplaceFragment.onClick(recordDatabase);
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
    ViewChildHolder viewHolder;
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_chlid_item, null);
        view.setOnClickListener(new View.OnClickListener() {
            RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
            @Override
            public void onClick(View view) {
                onRecordExpandableReplaceFragment.onClick(recordDatabase);
            }
        });
        viewHolder = new ViewChildHolder();
        viewHolder.record_child_item_begintime   = (TextView) view.findViewById(R.id.record_child_item_begintime);
        viewHolder.record_child_item_deadline    = (TextView) view.findViewById(R.id.record_child_item_deadline);
        viewHolder.record_child_item_remarks     = (TextView) view.findViewById(R.id.record_child_item_remarks);
        viewHolder.record_child_item_tag         = (TagContainerLayout) view.findViewById(R.id.record_child_item_tag);
        viewHolder.record_chile_item_step        = (NavigationTabStrip) view.findViewById(R.id.record_chile_item_step);
        viewHolder.record_child_item_parent      = (LinearLayout) view.findViewById(R.id.record_child_item_parent);
        viewHolder.record_chile_item_group       = (RadioGroup) view.findViewById(R.id.record_chile_item_group    );
        int step = recordDatabases.get(groupPosition).getStep();
        switch (step)
        {
            case 0:
                viewHolder.record_child_item_parent.setBackgroundResource(R.drawable.wait_shape);
                break;
            case 1:
                viewHolder.record_child_item_parent.setBackgroundResource(R.drawable.ongoing_shape);
                break;
            case 2:
                viewHolder.record_child_item_parent.setBackgroundResource(R.drawable.finish_shape);
                break;
        }
        int beginTime = recordDatabases.get(groupPosition).getBegin_time();
        viewHolder.record_child_item_begintime.setText(beginTime>-1? CalenderUtil.getInstance().changeToDate(beginTime):"未开始");
        viewHolder.record_child_item_deadline.setText(recordDatabases.get(groupPosition).getDeadline());
        viewHolder.record_chile_item_group.check(id[recordDatabases.get(groupPosition).getAlarm()]);
        String name = recordDatabases.get(groupPosition).getName();
        File refile = new File(context.getFilesDir(), name + ContentValueUtil.REMARKS);
        String des = FileUtil.readFile(refile);
        viewHolder.record_child_item_remarks.setText(des);
        File tagfile = new File(context.getFilesDir(), name + ContentValueUtil.TAG);
        String res = FileUtil.readFile(tagfile);
        try {
            JSONArray jsonArray = new JSONArray(res);
            for(int i =0;i<jsonArray.length();i++)
                viewHolder.record_child_item_tag.addTag(String.valueOf(jsonArray.get(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewHolder.record_chile_item_step.setTabIndex(recordDatabases.get(groupPosition).getStep());
        viewHolder.record_chile_item_step.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            LinearLayout temp = viewHolder.record_child_item_parent;
            View parentLayout = parent.getChildAt(groupPosition);
            TextView createDay = (TextView) parentLayout.findViewById(R.id.item_createDay);
            NavigationTabStrip strip = viewHolder.record_chile_item_step;
            RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
            TextView textView = viewHolder.record_child_item_begintime;
            @Override
            public void onStartTabSelected(String title, int index) {
            }

            @Override
            public void onEndTabSelected(String title, int index) {
                createDay.setText(steps[index]);
                recordDatabase.setStep(index);
                switch (index)
                {
                    case 0:
                        temp.setBackgroundResource(R.drawable.wait_shape);
                        parentLayout.setBackgroundResource(R.drawable.wait_shape);
                        textView.setText(steps[0]);
                        recordDatabase.setBegin_time(-1);
                        break;
                    case 1:
                        temp.setBackgroundResource(R.drawable.ongoing_shape);
                        parentLayout.setBackgroundResource(R.drawable.ongoing_shape);
                        recordDatabase.setBegin_time(CalenderUtil.getInstance().getDayFromOriginal());
                        textView.setText(CalenderUtil.getInstance().changeToDate(CalenderUtil.getInstance().getDayFromOriginal()));
                        break;
                    case 2:
                        temp.setBackgroundResource(R.drawable.finish_shape);
                        parentLayout.setBackgroundResource(R.drawable.finish_shape);
                        break;
                }
                RecordManager.getInstance().Add(recordDatabase);
            }
        });
        viewHolder.record_chile_item_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            RecordDatabase recordDatabase = recordDatabases.get(groupPosition);
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int alarm = 0;
                switch (i)
                {
                    case R.id.record_chile_item_email:
                        alarm = 0;
                        break;
                    case R.id.record_chile_item_alarm:
                        alarm = 1;
                        break;
                    case R.id.record_chile_item_no:
                        alarm = 2;
                }
                recordDatabase.setAlarm(alarm);
                RecordManager.getInstance().Add(recordDatabase);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class ViewChildHolder {
        LinearLayout record_child_item_parent;
        TextView   record_child_item_remarks;
        TextView record_child_item_deadline;
        TextView record_child_item_begintime;
        TagContainerLayout record_child_item_tag;
        NavigationTabStrip record_chile_item_step;
        RadioGroup record_chile_item_group;
    }
    class ViewHolder {
        LinearLayout   record_item_parent;
        TextView item_tv;
        MyRatingBar item_rating_bar;
        ImageButton item_delete;
        TextView item_createDay;
        ImageView item_image;
    }
}

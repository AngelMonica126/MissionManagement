package cn.monica.missionimpossible.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import java.io.File;
import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.RecordDatabase;
import cn.monica.missionimpossible.util.ContentValueUtil;
import cn.monica.missionimpossible.util.FileUtil;
import co.lujun.androidtagview.TagContainerLayout;

public class MainListAdapter extends BaseAdapter {
    private  List<RecordDatabase> recordDatebases;
    public  String[] tags;
    private Context context;


    public MainListAdapter(Context context, List<RecordDatabase>recordDatabases) {
        this.context = context;
        this.recordDatebases = recordDatabases;
        inti();
    }

    @Override
    public int getCount() {
        return tags.length;
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
            view = LayoutInflater.from(context).inflate(R.layout.main_class_item, null);
            viewHolder = new ViewHolder();
            viewHolder.choose_item_tag = (TagContainerLayout) view.findViewById(R.id.choose_item_tag);
            viewHolder.choose_ib = (ImageButton) view.findViewById(R.id.choose_ib);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.choose_item_tag.removeAllTags();

        }
        viewHolder.choose_item_tag.setTags(tags[position]);
        viewHolder.choose_ib.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//
                return true;
            }
        });
        return view;
    }

    class ViewHolder {
        TagContainerLayout choose_item_tag;
        ImageButton choose_ib;

    }

    private void judgeFileExists() {
        File logFile = new File(context.getFilesDir(), "tags");
        if (FileUtil.isEmpty(logFile)) {
            FileUtil.createNew(logFile);
        }
    }

    private void inti() {
        judgeFileExists();
        File file = new File(context.getFilesDir(), "tags");
        String res = FileUtil.readFile(file);
        if (res != null) {
            tags = res.split(ContentValueUtil.DIVIDE);

        }
    }
}
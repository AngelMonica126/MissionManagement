package cn.monica.missionimpossible.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Struct;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;

public class TitleShowView extends RelativeLayout {
    private  TitleViewStruct struct;
    private  Context context;
    private  TextView title;
    private  TextView textview;
    private  TextView time_textview;
    private  LinearLayout time_picker;

    public TitleShowView(Context context) {
        this(context,null,0);
    }
    public TitleShowView(Context context,  TitleViewStruct struct) {
        this(context,null,0);
        if(struct==null) return;
        this.struct = struct;
        setInfo();
    }

    private void setInfo() {
        title.setText(struct.getTitle());
        switch (struct.getType())
        {
            case EditText:
                textview.setVisibility(VISIBLE);
                textview.setText(struct.getInfo());
                break;
            case TimePicker:
                time_picker.setVisibility(VISIBLE);
                time_textview.setText(struct.getInfo());
                break;
        }
    }

    public TitleShowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
        if(attrs==null) return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleShowView);
        TitleViewType type= TitleViewType.values()[a.getInteger(R.styleable.TitleShowView_tstype,0)];
        this.struct = new TitleViewStruct(type,a.getString(R.styleable.TitleShowView_tstitle),a.getString(R.styleable.TitleShowView_tsinfo));
        setInfo();
    }

    private void initUI(Context context) {
        View view = (View) View.inflate(context, R.layout.title_show_view, this);
        title = (TextView) view.findViewById(R.id.title);
        textview = (TextView) view.findViewById(R.id.textview);
        time_textview = (TextView) view.findViewById(R.id.time_textview);
        time_picker = (LinearLayout) view.findViewById(R.id.time_picker);
    }
}

package cn.monica.missionimpossible.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Struct;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.myinterface.OnTitleViewDeletelistener;

public class TitleShowView extends RelativeLayout {
    private  OnTitleViewDeletelistener onTitleViewDeletelistener;
    private  TitleViewStruct struct;
    private  TextView title;
    private  TextView textview;
    private  LinearLayout time_picker;
    private ImageButton item_delete;

    public TitleShowView(Context context) {
        this(context,null,0);
    }
    public TitleShowView(Context context, TitleViewStruct struct, OnTitleViewDeletelistener onTitleViewDeletelistener) {
        this(context,null,0);
        this.onTitleViewDeletelistener = onTitleViewDeletelistener;
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
                break;
            case TimePicker:
                time_picker.setVisibility(VISIBLE);
                break;
        }
    }

    public TitleShowView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        time_picker = (LinearLayout) view.findViewById(R.id.time_picker);
        item_delete = (ImageButton)view.findViewById(R.id.item_delete);
        item_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTitleViewDeletelistener!=null)
                    onTitleViewDeletelistener.delete(TitleShowView.this);
            }
        });
    }
    public TitleViewStruct getInfo()
    {
        return  this.struct;
    }
}

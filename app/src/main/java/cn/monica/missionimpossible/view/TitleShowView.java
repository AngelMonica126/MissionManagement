package cn.monica.missionimpossible.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.myinterface.OnDatePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnTitleViewDeletelistener;
import cn.monica.missionimpossible.util.DialogHelper;

public class TitleShowView extends RelativeLayout {
    private  TitleViewStruct struct;
    private  Context context;
    private  TextView title;
    private  TextView textview;
    private  TextView time_textview;
    private  LinearLayout time_picker;
    private  TextView date_textview;
    private  LinearLayout date_picker;

    public TitleShowView(Context context) {
        this(context,null,0);
    }
    public TitleShowView(Context context,  TitleViewStruct struct) {
        this(context,null,0);
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
            case DatePicker:
                date_picker.setVisibility(VISIBLE);
                date_textview.setText(struct.getInfo());
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
    }

    private void initUI(Context context) {
        View view = (View) View.inflate(context, R.layout.titleview, this);
        title = (TextView) view.findViewById(R.id.title);
        textview = (TextView) view.findViewById(R.id.textview);

        time_textview = (TextView) view.findViewById(R.id.time_textview);
        time_picker = (LinearLayout) view.findViewById(R.id.time_picker);

        date_textview = (TextView) view.findViewById(R.id.date_textview);
        date_picker = (LinearLayout) view.findViewById(R.id.date_picker);
    }
}

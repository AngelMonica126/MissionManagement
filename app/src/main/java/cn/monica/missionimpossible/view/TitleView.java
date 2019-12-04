package cn.monica.missionimpossible.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
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

public class TitleView extends RelativeLayout {
    private String titleText;
    private  Context context;
    private  OnTitleViewDeletelistener onTitleViewDeletelistener;
    private  TextView title;
    private  TextView textview;
    private  TextView time_textview;
    private  ImageButton time_button;
    private  LinearLayout time_picker;
    private  TextView date_textview;
    private  ImageButton date_button;
    private  LinearLayout date_picker;
    private  ImageButton delete;
    private  EditText   editText;
    private TitleViewType type;

    public TitleView(Context context) {
        this(context,null);
    }
    public TitleView(Context context, OnTitleViewDeletelistener onTitleViewDeletelistener,String titleText) {
        this(context,null,0);
        this.onTitleViewDeletelistener = onTitleViewDeletelistener;
        this.titleText = titleText;
        title.setText(titleText);
    }
    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
        setOnclick();
    }

    private void setOnclick() {
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onTitleViewDeletelistener.delete(TitleView.this);
            }
        });
        time_button.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                DialogHelper.getInstance().createTimePickerDialog(context, new OnTimePickerDialogInterface() {
                    @Override
                    public void Save(int hours, int minutes) {
                        time_textview.setText(hours+":"+minutes);
                    }
                });
            }
        });
        date_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.getInstance().createDatePickerDialog(context, new OnDatePickerDialogInterface() {
                    @Override
                    public void Save(int year, int mouth, int day) {
                        date_textview.setText(year+"/"+mouth+"/"+day);
                    }
                });
            }
        });
    }

    private void initUI(Context context) {
        View view = (View) View.inflate(context, R.layout.titleview, this);
        title = (TextView) view.findViewById(R.id.title);

        delete = (ImageButton) view.findViewById(R.id.item_delete);
        editText = (EditText) view.findViewById(R.id.edittext);

        textview = (TextView) view.findViewById(R.id.textview);

        time_textview = (TextView) view.findViewById(R.id.time_textview);
        time_button = (ImageButton) view.findViewById(R.id.time_button);
        time_picker = (LinearLayout) view.findViewById(R.id.time_picker);

        date_textview = (TextView) view.findViewById(R.id.date_textview);
        date_button = (ImageButton) view.findViewById(R.id.date_button);
        date_picker = (LinearLayout) view.findViewById(R.id.date_picker);
    }
    public void setType(TitleViewType type)
    {
        this.type = type;
        switch (type)
        {
            case EditText:
                editText.setVisibility(VISIBLE);
                break;
            case TimePicker:
                time_picker.setVisibility(VISIBLE);
                break;
            case DatePicker:
                date_picker.setVisibility(VISIBLE);
                break;
        }
    }
    public TitleViewStruct getInfo()
    {
        String info = null;
        switch (type)
        {
            case EditText:
                info = editText.getText().toString().trim();
                break;
            case TimePicker:
                info = time_textview.getText().toString().trim();
                break;
            case DatePicker:
                info = date_textview.getText().toString().trim();
                break;
        }
        return new TitleViewStruct(type,title.getText().toString().trim(),info);
    }
}

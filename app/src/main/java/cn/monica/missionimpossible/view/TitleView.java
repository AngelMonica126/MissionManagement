package cn.monica.missionimpossible.view;

import android.content.Context;
import android.content.res.TypedArray;
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
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnTitleViewDeletelistener;
import cn.monica.missionimpossible.util.DialogHelper;

public class TitleView extends RelativeLayout {
    private  Context context;
    private  TextView title;
    private  TextView textview;
    private  TextView time_textview;
    private  ImageButton time_button;
    private  LinearLayout time_picker;
    private  EditText   editText;
    private TitleViewStruct struct;
    public TitleView(Context context) {
        this(context,null,0);
    }
    public TitleView(Context context, TitleViewStruct struct) {
        this(context,null,0);
        this.struct = struct;
        title.setText(this.struct.getTitle());
        setType(this.struct.getType());
    }
    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
        setOnclick();
        if(attrs==null) return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        TitleViewType type= TitleViewType.values()[a.getInteger(R.styleable.TitleView_ttype,0)];
        setType(type);
        title.setText(a.getString(R.styleable.TitleView_ttitle));
    }

    private void setOnclick() {
        time_button.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                DialogHelper.getInstance().createTimePickerDialog(context, new OnTimePickerDialogInterface() {
                    @Override
                    public void Save(int year, int mouth, int day, int hours, int minutes) {
                        time_textview.setText(year+"-"+mouth+"-"+day+" "+hours+":"+minutes);
                    }
                });
            }
        });
    }

    private void initUI(Context context) {
        View view = (View) View.inflate(context, R.layout.title_view, this);
        title = (TextView) view.findViewById(R.id.title);
        editText = (EditText) view.findViewById(R.id.edittext);
        textview = (TextView) view.findViewById(R.id.textview);
        time_textview = (TextView) view.findViewById(R.id.time_textview);
        time_button = (ImageButton) view.findViewById(R.id.time_button);
        time_picker = (LinearLayout) view.findViewById(R.id.time_picker);
    }
    public void setType(TitleViewType type)
    {
        switch (type)
        {
            case EditText:
                editText.setVisibility(VISIBLE);
                break;
            case TimePicker:
                time_picker.setVisibility(VISIBLE);
                break;
        }
    }
    public TitleViewStruct getInfo()
    {
        String info = null;
        switch (this.struct.getType())
        {
            case EditText:
                info = editText.getText().toString().trim();
                break;
            case TimePicker:
                info = time_textview.getText().toString().trim();
                break;
        }
        return new TitleViewStruct(this.struct.getType(),title.getText().toString().trim(),info);
    }
}

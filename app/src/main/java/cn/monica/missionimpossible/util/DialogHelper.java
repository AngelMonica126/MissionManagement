package cn.monica.missionimpossible.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.myinterface.OnRemindActionListener;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnViewChooseListener;
import cn.monica.missionimpossible.view.TitleShowView;
import cn.monica.missionimpossible.view.TitleView;
import co.lujun.androidtagview.TagContainerLayout;

public class DialogHelper {
    private static DialogHelper dialogHelper = new DialogHelper();
    boolean mark = false;
    public static DialogHelper getInstance() {
        return dialogHelper;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createTimePickerDialog(Context context, final OnTimePickerDialogInterface onTimePickerDialogInterface)
    {
        mark = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(context );
        final AlertDialog alertDialog = builder.create();
        final View view = View.inflate(context, R.layout.timepicker_dialog, null);
        alertDialog.setView(view);
        alertDialog.show();
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.dialog_time);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.dialog_date);
        final Button button = (Button) view.findViewById(R.id.dialog_confirm);
        Date date = new Date(System.currentTimeMillis());
        timePicker.setIs24HourView(true);
        timePicker.setHour(date.getHours());
        timePicker.setMinute(date.getMinutes());
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year,monthOfYear,dayOfMonth,null);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(!mark)
                {
                    mark = true;
                    button.setText("保存");
                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.VISIBLE);
                }
                else
                {
                    onTimePickerDialogInterface.Save(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth(),timePicker.getHour(),timePicker.getMinute());
                    alertDialog.dismiss();
                }
            }
        });
    }

    public void createRemindDialog(Context context, RecordDatabase recordDatabase, final OnRemindActionListener onRemindActionListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context );
        final AlertDialog alertDialog = builder.create();
        final View view = View.inflate(context, R.layout.remind_dialog, null);
        alertDialog.setView(view);
        alertDialog.show();
        TextView  remind_dialog_title = (TextView) view.findViewById(R.id.remind_dialog_title);
        TextView  remind_dialog_remarks = (TextView) view.findViewById(R.id.remind_dialog_remarks);
        TitleView remind_dialog_deadline = (TitleView) view.findViewById(R.id.remind_dialog_deadline);
        TagContainerLayout remind_dialog_tag = (TagContainerLayout) view.findViewById(R.id.remind_dialog_tag);
        //setTitle
        remind_dialog_title.setText(recordDatabase.getTitle());

        File remarksFile = new File(context.getFilesDir(), recordDatabase.getName() + ContentValueUtil.REMARKS);
        String remarks = FileUtil.readFile(remarksFile);
        remind_dialog_remarks.setText(remarks);

        remind_dialog_deadline.setInfo(recordDatabase.getDeadline());

        File tagFile = new File(context.getFilesDir(), recordDatabase.getName() + ContentValueUtil.TAG);
        try {
            JSONArray jsonArray = new JSONArray(FileUtil.readFile(tagFile));
            for(int i = 0 ;i <jsonArray.length();i++)
                remind_dialog_tag.addTag(String.valueOf(jsonArray.get(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button remind_dialog_confirm = (Button) view.findViewById(R.id.remind_dialog_confirm);
        Button remind_dialog_delay = (Button) view.findViewById(R.id.remind_dialog_delay);
        Button remind_dialog_view = (Button) view.findViewById(R.id.remind_dialog_view);
        remind_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onRemindActionListener.Confirm();
            }
        });
        remind_dialog_delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onRemindActionListener.Delay();
            }
        });
        remind_dialog_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onRemindActionListener.View();
            }
        });
    }
//    public void createDatePickerDialog(Context context, final OnDatePickerDialogInterface onDatePickerDialogInterface)
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context );
//        final AlertDialog alertDialog = builder.create();
//        final View view = View.inflate(context, R.layout.datepicker_dialog, null);
//        alertDialog.setView(view);
//        alertDialog.show();
//        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date);
//        Date date = new Date(System.currentTimeMillis());
//        Calendar calendar = Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);
//        int monthOfYear=calendar.get(Calendar.MONTH);
//        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
//        datePicker.init(year,monthOfYear,dayOfMonth,null);
//        Button button = (Button) view.findViewById(R.id.dialog_confirm);
//        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                onDatePickerDialogInterface.Save(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
//                alertDialog.dismiss();
//            }
//        });
//    }
    public void createViewChooseDialog(final Context context, final OnViewChooseListener onViewChooseListener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context );
        final AlertDialog alertDialog = builder.create();
        final View view = View.inflate(context, R.layout.view_choose_dialog, null);
        alertDialog.setView(view);
        alertDialog.show();
        final RadioGroup group = (RadioGroup) view.findViewById(R.id.group);
        Button button = (Button) view.findViewById(R.id.dialog_confirm);
        final EditText editText = (EditText) view.findViewById(R.id.edittext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editText.getText().toString()))
                    ToastUtil.makeToast(context,"请输入标题");
                else
                {
                    int id = 0;
                    switch (group.getCheckedRadioButtonId())
                    {
                        case R.id.radio1:
                            id = 0;
                            break;
                        case R.id.radio2:
                            id = 1;
                            break;
                    }
                    TitleViewType type= TitleViewType.values()[id];
                    onViewChooseListener.save(type,editText.getText().toString().trim());
                    alertDialog.dismiss();
                }

            }
        });
    }
}

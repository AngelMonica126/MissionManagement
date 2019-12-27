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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnViewChooseListener;

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

package cn.monica.missionimpossible.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Date;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;

public class DialogHelper {
    private static DialogHelper dialogHelper = new DialogHelper();
    public static DialogHelper getInstance() {
        return dialogHelper;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createTimePickerDialog(Context context, final OnTimePickerDialogInterface onTimePickerDialogInterface)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context );
        final AlertDialog alertDialog = builder.create();
        final View view = View.inflate(context, R.layout.timepicker_dialog, null);
        alertDialog.setView(view);
        alertDialog.show();
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time);
        Date date = new Date(System.currentTimeMillis());
        timePicker.setIs24HourView(true);
        timePicker.setHour(date.getHours());
        timePicker.setMinute(date.getMinutes());
        Button button = (Button) view.findViewById(R.id.dialog_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                onTimePickerDialogInterface.Save(timePicker.getHour(),timePicker.getMinute());
                alertDialog.dismiss();
            }
        });
    }
}

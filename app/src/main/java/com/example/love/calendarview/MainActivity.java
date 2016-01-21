package com.example.love.calendarview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private MyCalendarView myCalendarView;
    private AlertDialog dialog_date;
    private Context mContext;
    private MyCalendarViewForSchedule dpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_date==null){
                    chooseDateByMaterial();
                }else {
                    dialog_date.show();
                }
            }
        });

    }


    protected void chooseDateByMaterial() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_date_picker_for_schedule, null);
        dialog_date = new AlertDialog.Builder(mContext, R.style.dialog).create();
        dialog_date.show();
        dialog_date.getWindow().setContentView(view);
        WindowManager.LayoutParams lp = dialog_date.getWindow().getAttributes();
        //dialog_date.getWindow().setGravity(Gravity.BOTTOM);
        dialog_date.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        //dialog_date.getWindow().setWindowAnimations(R.style.myDialogAnimStyle);
        // 铺满整个屏幕
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog_date.getWindow().setAttributes(lp);

        dpDialog = (MyCalendarViewForSchedule) view.findViewById(R.id.dp_dialog);
        Button btn_dpDialog = (Button) view.findViewById(R.id.btn_dpDialog);
        btn_dpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_date.dismiss();
                Toast.makeText(mContext,"开始日期："+ dpDialog.hasDays.get(0)+"结束日期："+dpDialog.hasDays.get(0),Toast.LENGTH_LONG).show();
            }
        });

        dpDialog.hasDays.add(TimestampTool.getCurrentDateToWeb());//开始
        dpDialog.hasDays.add(TimestampTool.getCurrentDateToWeb());//结束
        dpDialog.outerNotifyDataSetChanged();

    }

}

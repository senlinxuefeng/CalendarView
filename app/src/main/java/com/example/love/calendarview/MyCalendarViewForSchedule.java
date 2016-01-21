package com.example.love.calendarview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mrper on 2015/5/30.
 * 日历视图控件
 */
public class MyCalendarViewForSchedule extends LinearLayout {


    private RelativeLayout rl_startDate;
    private RelativeLayout rl_endDate;
    private TextView tv_currentStartDate;
    private TextView tv_currentEndDate;
    private String currentYearAndMonth;
    private CalendarAdapter calendarAdapter;
    private TextView view_startDate;
    private TextView view_endDate;
    private TextView tv_backToToday;
    private String currentDate;
    private int lastX;
    private int lastY;
    private int startX;
    private int startY;
    public TextView tv_title;

    //即清空时设置的日期
    public String clearDate = "1985-01-01 00:00:00";

    /**
     * 日期信息实体类
     **/
    public class DayInfo {
        public int day;
        public DayType dayType;

        @Override
        public String toString() {
            return String.valueOf(day);
        }
    }

    /**
     * 日期类型
     **/
    public enum DayType {
        DAY_TYPE_NONE(0),
        DAY_TYPE_FORE(1),
        DAY_TYPE_NOW(2),
        DAY_TYPE_NEXT(3);
        private int value;

        DayType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Context context;//上下文对象
    private TextView txtTitle;//标题文字
    private GridViewStopSlide dateGrid;//日期表格
    private final Calendar calendar = Calendar.getInstance();
    private static final int MAX_DAY_COUNT = 42;//最大格子数量
    private DayInfo[] dayInfos = new DayInfo[MAX_DAY_COUNT];//每月应该有的天数，36为最大格子数

    public List<String> hasDays = new ArrayList<String>();//已经选中的日期
    private boolean isFirst = true;


    public MyCalendarViewForSchedule(Context context) {
        super(context);
        init(context);//初始化程序
        showCalendar(calendar);//显示日历数据
    }

    public MyCalendarViewForSchedule(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentDate = TimestampTool.getCurrentDateToWeb();
        hasDays.add(currentDate);
        init(context);//初始化程序
        showCalendar(calendar);//显示日历数据
    }

    /**
     * 更新界面
     */
    public void outerNotifyDataSetChanged() {
        if (hasDays != null && hasDays.size() == 3) {
            if (hasDays.get(1).equals(hasDays.get(2))) {
                String tempDate = new SimpleDateFormat("M月d").format(new Date(TimestampTool.getSomdayTime(hasDays.get(1))));
                tv_currentStartDate.setText(tempDate);
                tv_currentEndDate.setText(tempDate);
                view_endDate.setSelected(false);
                view_startDate.setSelected(false);
            } else {
                String startDate = new SimpleDateFormat("M月d").format(new Date(TimestampTool.getSomdayTime(hasDays.get(1))));
                String endDate = new SimpleDateFormat("M月d").format(new Date(TimestampTool.getSomdayTime(hasDays.get(2))));
                tv_currentStartDate.setText(startDate);
                tv_currentEndDate.setText(endDate);
                view_endDate.setSelected(true);
                view_startDate.setSelected(false);
            }
        }
        calendarAdapter.notifyDataSetChanged();
    }

    /**
     * 更新界面
     */
    private void innerNotifyDataSetChanged(String innerRefresh) {

        if (hasDays != null && hasDays.size() == 3) {
            if (hasDays.get(1).equals(hasDays.get(2))) {
                String tempDate = new SimpleDateFormat("M月d").format(new Date(TimestampTool.getSomdayTime(hasDays.get(1))));
                tv_currentStartDate.setText(tempDate);
                tv_currentEndDate.setText(tempDate);
            } else {
                String startDate = new SimpleDateFormat("M月d").format(new Date(TimestampTool.getSomdayTime(hasDays.get(1))));
                String endDate = new SimpleDateFormat("M月d").format(new Date(TimestampTool.getSomdayTime(hasDays.get(2))));
                tv_currentStartDate.setText(startDate);
                tv_currentEndDate.setText(endDate);
            }
        }
        calendarAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化程序
     **/
    private void init(Context context) {
        this.context = context;
        View rootView = View.inflate(context, R.layout.calendar_for_schedule, null);

        rl_startDate = (RelativeLayout) rootView.findViewById(R.id.rl_startDate);
        rl_endDate = (RelativeLayout) rootView.findViewById(R.id.rl_endDate);
        tv_currentStartDate = (TextView) rootView.findViewById(R.id.tv_currentStartDate);
        tv_currentEndDate = (TextView) rootView.findViewById(R.id.tv_currentEndDate);
        view_startDate = (TextView) rootView.findViewById(R.id.view_startDate);
        view_endDate = (TextView) rootView.findViewById(R.id.view_endDate);
        tv_backToToday = (TextView) rootView.findViewById(R.id.tv_backToToday);
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);

        rl_startDate.setOnClickListener(onClickListener);
        rl_endDate.setOnClickListener(onClickListener);
        tv_backToToday.setOnClickListener(onClickListener);


        dateGrid = (GridViewStopSlide) rootView.findViewById(R.id.widgetCalendar_calendar);
        txtTitle = (TextView) rootView.findViewById(R.id.widgetCalendar_txtTitle);
        rootView.findViewById(R.id.widgetCalendar_imgForeYear)
                .setOnClickListener(navigatorClickListener);
        rootView.findViewById(R.id.widgetCalendar_imgForeMonth)
                .setOnClickListener(navigatorClickListener);
        rootView.findViewById(R.id.widgetCalendar_imgNextMonth)
                .setOnClickListener(navigatorClickListener);
        rootView.findViewById(R.id.widgetCalendar_imgNextYear)
                .setOnClickListener(navigatorClickListener);
        this.setOrientation(VERTICAL);//设置布局方向
        this.addView(rootView);//添加根视图
    }


    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_startDate:
                    if (hasDays != null && hasDays.size() == 1) {
                        hasDays.add(hasDays.get(0));
                    }
                   // ToastUtil.shortToast(context, "rl_startDate" + hasDays.size());
                    view_startDate.setSelected(true);
                    view_endDate.setSelected(false);
                    innerNotifyDataSetChanged("inner");
                    break;

                case R.id.rl_endDate:
                    if (hasDays != null && hasDays.size() == 1) {
                        if (TimestampTool.getSomdayTime(hasDays.get(0)) < TimestampTool.getSomdayTime(currentDate)) {
                            hasDays.clear();
                            hasDays.add(currentDate);
                            hasDays.add(currentDate);
                        } else {
                            hasDays.add(hasDays.get(0));
                        }
                    } else if (hasDays != null && hasDays.size() == 2) {
                        if (TimestampTool.getSomdayTime(hasDays.get(0)) < TimestampTool.getSomdayTime(currentDate)) {
                            hasDays.clear();
                            hasDays.add(currentDate);
                            hasDays.add(currentDate);
                        } else {
                            hasDays.add(hasDays.get(0));
                        }
                    }
                    //ToastUtil.shortToast(context, "rl_endDate" + hasDays.size());
                    view_startDate.setSelected(false);
                    view_endDate.setSelected(true);
                    innerNotifyDataSetChanged("inner");
                    break;
                case R.id.tv_backToToday:
                    if (tv_title.getVisibility()==View.VISIBLE){
                        tv_title.setVisibility(View.GONE);
                    }else {
                        tv_title.setVisibility(View.GONE);
                    }
                    view_startDate.setSelected(false);
                    view_endDate.setSelected(false);
                    hasDays.clear();
                    hasDays.add(TimestampTool.getCurrentDateToWeb());
                    hasDays.add(TimestampTool.getCurrentDateToWeb());
                    hasDays.add(TimestampTool.getCurrentDateToWeb());
                    calendar.clear();
                    calendar.setTime(new Date(TimestampTool.getSomdayTime(TimestampTool.getCurrentDateToWeb())));
                    showCalendar(calendar);
                    innerNotifyDataSetChanged("inner");
                    break;


            }
        }
    };

    /**
     * 显示日历数据
     **/
    private void showCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);//获得年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
//        int day = calendar.get(Calendar.DATE);//获取天数
        int centry = Integer.valueOf(String.valueOf(year).substring(0, 2));//取年份前两位作为世纪数,世纪数-1
        int tmpYear = Integer.valueOf(String.valueOf(year).substring(2, 4));//取年份后两位
        if (month == 1 || month == 2) {//该年的1、2月看作为前一年的13月，14月
            tmpYear -= 1;
            month += 12;
        }
        //计算该月的第一天是星期几
        int firstOfWeek = (tmpYear + (tmpYear / 4) + centry / 4 - 2 * centry + 26 * (month + 1) / 10) % 7;
        if (firstOfWeek <= 0) firstOfWeek = 7 + firstOfWeek;//处理星期的显示
        //计算第一天所在的索引值,如果该天为星期一，则做换行处理
        final int firstDayIndex = firstOfWeek == 1 ? 7 : firstOfWeek;
        final int dayCount = getDayCount(year, month);//获取该月的天数
        //处理本月的数据
        for (int i = firstDayIndex; i < firstDayIndex + dayCount; i++) {
            if (dayInfos[i] == null)
                dayInfos[i] = new DayInfo();
            dayInfos[i].day = i - firstDayIndex + 1;
            dayInfos[i].dayType = DayType.DAY_TYPE_NOW;
        }
        //处理前一个月的数据
        calendar.add(Calendar.MONTH, -1);//前一个月
        year = calendar.get(Calendar.YEAR);//获得年份
        month = calendar.get(Calendar.MONTH) + 1;//获取月份
        final int foreDayCount = getDayCount(year, month);//获得前一个月的天数
        for (int i = 0; i < firstDayIndex; i++) {
            if (dayInfos[i] == null)
                dayInfos[i] = new DayInfo();
            dayInfos[i].day = foreDayCount - firstDayIndex + i + 1;
            dayInfos[i].dayType = DayType.DAY_TYPE_FORE;
        }
        //处理下一个月的数据
        for (int i = 0; i < MAX_DAY_COUNT - dayCount - firstDayIndex; i++) {
            if (dayInfos[firstDayIndex + dayCount + i] == null)
                dayInfos[firstDayIndex + dayCount + i] = new DayInfo();
            dayInfos[firstDayIndex + dayCount + i].day = i + 1;
            dayInfos[firstDayIndex + dayCount + i].dayType = DayType.DAY_TYPE_NEXT;
        }
        calendar.add(Calendar.MONTH, 1);//还原月份数据
        txtTitle.setText(new SimpleDateFormat("yyyy-M").format(calendar.getTime()));//设置日历显示的标题
        // "2015-11-30 17:11:01",
        currentYearAndMonth = (new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        currentYearAndMonth = currentYearAndMonth.substring(0, currentYearAndMonth.length() - 2);
        calendarAdapter = new CalendarAdapter(context, dayInfos);
        dateGrid.setAdapter(calendarAdapter);
    }

    /**
     * 导航按钮点击事件
     **/
    private OnClickListener navigatorClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.widgetCalendar_imgForeMonth://上一月
                    calendar.add(Calendar.MONTH, -1);
                    break;
                case R.id.widgetCalendar_imgForeYear://上一年
                    calendar.add(Calendar.YEAR, -1);
                    break;
                case R.id.widgetCalendar_imgNextMonth://下一月
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case R.id.widgetCalendar_imgNextYear://下一年
                    calendar.add(Calendar.YEAR, 1);
                    break;
            }
            showCalendar(calendar);//显示日历数据
        }
    };

    /**
     * 是否是平年
     **/
    private boolean isLeapYear(int year) {
        return !((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    /**
     * 获取某年的某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    private int getDayCount(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13://其实是1月，当作上一年的13月看待
                return 31;
            case 2:
            case 14://其实是2月，当作上一年的14月看
                return isLeapYear(year) ? 28 : 29;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return 0;
    }

    /**
     * 判断两个Calendar中的日期是否相等
     *
     * @param calendar
     * @param calendar1
     * @return
     */
    private boolean isDateEqual(Calendar calendar, Calendar calendar1) {
        return (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == calendar1.get(Calendar.DATE));
    }

    /**
     * 日历数据适配器
     **/
    public class CalendarAdapter extends BaseAdapter {

        private Context context;
        private List<DayInfo> dayInfos = new ArrayList<>();

        public CalendarAdapter(Context context, DayInfo[] dayInfos) {
            this.context = context;
            if (dayInfos != null && dayInfos.length > 0) {
                this.dayInfos.addAll(Arrays.asList(dayInfos));
            }
        }

        @Override
        public int getCount() {
            return dayInfos == null ? 0 : dayInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return dayInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            final DayInfo item = dayInfos.get(position);
            if (convertView == null) {
                //convertView = new TextView(context);
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
                viewHolder.fl_date = (FrameLayout) convertView.findViewById(R.id.fl_date);
                viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(viewHolder);
                viewHolder.tv_date.getPaint().setFakeBoldText(true);
                viewHolder.tv_date.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f);
            }
            viewHolder = (ViewHolder) convertView.getTag();

            if (item.dayType == DayType.DAY_TYPE_FORE || item.dayType == DayType.DAY_TYPE_NEXT) {//标识文字颜色
                viewHolder.tv_date.setTextColor(Color.DKGRAY);
            } else {
                viewHolder.tv_date.setTextColor(Color.BLACK);
            }
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTimeInMillis(calendar.getTimeInMillis());
            tmpCalendar.set(Calendar.DAY_OF_MONTH, item.day);

            if (item.dayType == DayType.DAY_TYPE_NOW && hasDays != null && hasDays.size() == 3 && hasDays.get(1).equals(hasDays.get(2))) {
                viewHolder.tv_date.setText(item.toString());
                if (hasDays.get(1).equals(currentYearAndMonth + String.format("%02d", Integer.parseInt(item.toString())) + " 00:00:00")) {
                    viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.white));
                    viewHolder.tv_date.setBackgroundResource(R.drawable.shape_calender_circle);
                } else {
                    viewHolder.tv_date.setBackgroundColor(context.getResources().getColor(R.color.white));
                    if (view_endDate.isSelected() && tmpCalendar.getTimeInMillis() < TimestampTool.getSomdayTime(hasDays.get(1))) {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.gray));
                    } else if (view_startDate.isSelected() && tmpCalendar.getTimeInMillis() > TimestampTool.getSomdayTime(hasDays.get(1))) {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.gray));
                    } else {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.black));
                    }

                    if (tmpCalendar.getTimeInMillis() < TimestampTool.getSomdayTime(hasDays.get(0))) {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.gray));
                    }

                    if (position %7 ==0||position%7 == 6){
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    }
                }
            } else if (item.dayType == DayType.DAY_TYPE_NOW && hasDays.size() == 3 && !hasDays.get(1).equals(hasDays.get(2))) {
                viewHolder.tv_date.setText(item.toString());
                if (tmpCalendar.getTimeInMillis() >= TimestampTool.getSomdayTime(hasDays.get(1)) && tmpCalendar.getTimeInMillis() < (TimestampTool.getSomdayTime(hasDays.get(2)) + 86400000)) {
                    viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.white));
                    //viewHolder.tv_date.setBackgroundColor(context.getResources().getColor(R.color.bg_color));
                    viewHolder.tv_date.setBackgroundResource(R.drawable.shape_calender_circle);
                } else {
                    viewHolder.tv_date.setBackgroundColor(context.getResources().getColor(R.color.white));
                    if (view_endDate.isSelected() && tmpCalendar.getTimeInMillis() < TimestampTool.getSomdayTime(hasDays.get(1))) {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.gray));
                    } else if (view_startDate.isSelected() && tmpCalendar.getTimeInMillis() > TimestampTool.getSomdayTime(hasDays.get(2))) {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.gray));
                    } else {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.black));
                    }

                    if (tmpCalendar.getTimeInMillis() < TimestampTool.getSomdayTime(hasDays.get(0))) {
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.gray));
                    }

                    if (position %7 == 0||position%7 == 6){
                        viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    }
                }
            } else {
                viewHolder.tv_date.setText("");
                viewHolder.tv_date.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (item.dayType) {//根据类型判断应该处理的事件
                        case DAY_TYPE_FORE://跳转至前一个月
                            //Toast.makeText(context,item.toString(),Toast.LENGTH_LONG).show();
                            //calendar.add(Calendar.MONTH, -1);
                            //showCalendar(calendar);//显示日历数据
                            break;
                        case DAY_TYPE_NOW:
                            String currentSomeDate = currentYearAndMonth + String.format("%02d", Integer.parseInt(item.toString())) + " 00:00:00";
                            if (!view_startDate.isSelected() && !view_endDate.isSelected() && TimestampTool.getSomdayTime(currentSomeDate) >= TimestampTool.getSomdayTime(hasDays.get(0))) {
                               if (tv_title.getVisibility()==View.VISIBLE){
                                   tv_title.setVisibility(View.GONE);
                               }else {
                                   tv_title.setVisibility(View.GONE);
                               }
                                hasDays.clear();
                                hasDays.add(currentDate);
                                hasDays.add(currentSomeDate);
                                hasDays.add(currentSomeDate);
                                innerNotifyDataSetChanged("inner");
                                //ToastUtil.shortToast(context, "沒有選中開始和結束的日期" + hasDays.toString());
                            } else if (view_startDate.isSelected() && !view_endDate.isSelected() && TimestampTool.getSomdayTime(currentSomeDate) <= TimestampTool.getSomdayTime(hasDays.get(2)) && TimestampTool.getSomdayTime(currentSomeDate) >= TimestampTool.getSomdayTime(currentDate)) {
                                if (tv_title.getVisibility()==View.VISIBLE){
                                    tv_title.setVisibility(View.GONE);
                                }else {
                                    tv_title.setVisibility(View.GONE);
                                }
                                if (TimestampTool.getSomdayTime(currentSomeDate) < TimestampTool.getSomdayTime(hasDays.get(2))) {
                                    hasDays.remove(1);
                                    hasDays.add(1, currentSomeDate);
                                    innerNotifyDataSetChanged("inner");
                                    // ToastUtil.shortToast(context, "選中開始日期" + hasDays.toString());
                                } else if (TimestampTool.getSomdayTime(currentSomeDate) == TimestampTool.getSomdayTime(hasDays.get(2))) {
                                    hasDays.clear();
                                    hasDays.add(currentDate);
                                    hasDays.add(currentSomeDate);
                                    hasDays.add(currentSomeDate);
                                    view_endDate.setSelected(false);
                                    view_startDate.setSelected(false);
                                    innerNotifyDataSetChanged("inner");
                                }

                            } else if (!view_startDate.isSelected() && view_endDate.isSelected() && TimestampTool.getSomdayTime(currentSomeDate) >= TimestampTool.getSomdayTime(hasDays.get(1)) && TimestampTool.getSomdayTime(currentSomeDate) >= TimestampTool.getSomdayTime(currentDate)) {
                                if (tv_title.getVisibility()==View.VISIBLE){
                                    tv_title.setVisibility(View.GONE);
                                }else {
                                    tv_title.setVisibility(View.GONE);
                                }
                                if (TimestampTool.getSomdayTime(hasDays.get(1)) < TimestampTool.getSomdayTime(currentDate)) {
                                    hasDays.remove(1);
                                    hasDays.add(1, currentDate);
                                }
                                if (TimestampTool.getSomdayTime(currentSomeDate) > TimestampTool.getSomdayTime(hasDays.get(1))) {
                                    hasDays.remove(2);
                                    hasDays.add(currentSomeDate);
                                    innerNotifyDataSetChanged("inner");
                                    //ToastUtil.shortToast(context, "選中結束日期" + hasDays.toString());
                                } else if (TimestampTool.getSomdayTime(currentSomeDate) == TimestampTool.getSomdayTime(hasDays.get(1))) {
                                    hasDays.clear();
                                    hasDays.add(currentDate);
                                    hasDays.add(currentSomeDate);
                                    hasDays.add(currentSomeDate);
                                    view_endDate.setSelected(false);
                                    view_startDate.setSelected(false);
                                    innerNotifyDataSetChanged("inner");
                                }


                            } else {

                            }
                            break;
                        case DAY_TYPE_NEXT://跳转至下一个月
                            //Toast.makeText(context,item.toString(), Toast.LENGTH_LONG).show();
                            //calendar.add(Calendar.MONTH,1);
                            // showCalendar(calendar);//显示日历数据
                            //Toast.makeText(context,item.toString(),1000).show();
                            break;
                    }
                }
            };
            viewHolder.tv_date.setOnClickListener(listener);//设置日期点击事件

            //convertView.setSelected(true);
            return convertView;
        }
    }

    public class ViewHolder {
        private FrameLayout fl_date;
        private TextView tv_date;
    }


}

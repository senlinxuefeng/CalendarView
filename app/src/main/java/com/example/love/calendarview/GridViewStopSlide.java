package com.example.love.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by love on 2015/12/24.
 */
public class GridViewStopSlide extends GridView{
    public GridViewStopSlide(Context context) {
        super(context);
    }

    public GridViewStopSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewStopSlide(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
        {
            return true;// 禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }
}

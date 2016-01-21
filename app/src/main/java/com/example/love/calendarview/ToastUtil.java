package com.example.love.calendarview;

import android.content.Context;
import android.widget.Toast;

/**
 * 只显示一次的吐司
 */
public class ToastUtil {
	   private static Context context = null;
	    private static Toast toast = null;

	    public static void shortToast(Context context,int retId){
	        if (toast == null) {
	            toast = Toast.makeText(context, retId, Toast.LENGTH_SHORT);
	        } else {
	            toast.setText(retId);
	            toast.setDuration(Toast.LENGTH_SHORT);
	        }
	        toast.show();
	    }


	    public static void shortToast(Context context,String hint){
	        if (toast == null) {
	            toast = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
	        } else {
	            toast.setText(hint);
	            toast.setDuration(Toast.LENGTH_SHORT);
	        }
	        toast.show();
	    }


	    public static void longToast(Context context,int retId){
	        if (toast == null) {
	            toast = Toast.makeText(context, retId, Toast.LENGTH_LONG);
	        } else {
	            toast.setText(retId);
	            toast.setDuration(Toast.LENGTH_LONG);
	        }
	        toast.show();
	    }


	    public static void longToast(Context context,String hint){
	        if (toast == null) {
	            toast = Toast.makeText(context, hint, Toast.LENGTH_LONG);
	        } else {
	            toast.setText(hint);
	            toast.setDuration(Toast.LENGTH_LONG);
	        }
	        toast.show();
	    }
}

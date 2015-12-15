package com.nongziwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView{

	 private OnScrollToBottomListener onScrollToBottom;  
     
	    public MyScrollView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	    }  
	  
	    public MyScrollView(Context context) {  
	        super(context);  
	    }  
	  
	    @Override  
	    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,  
	            boolean clampedY) {  
	        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);  
	        if(scrollY != 0 && null != onScrollToBottom){  
	            onScrollToBottom.onScrollBottomListener(clampedY);  
	        }  
	    }  
	      
	    
	    
	    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener){  
	        onScrollToBottom = listener;  
	    }  
	  
	    public interface OnScrollToBottomListener{  
	        public void onScrollBottomListener(boolean isBottom);  
	    }  

}

package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyViewPager extends ViewGroup {

    private final Context context;
    private GestureDetector gestureDetector;
    private float startX;
    private float endX;
    private int position = 0;
    private Scroller scroller;


    public MyViewPager(Context context) {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();

    }

    private void initView() {

        scroller = new Scroller(context);

        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX,getScrollY());

                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);
         gestureDetector.onTouchEvent(event);
         switch (event.getAction()){
             case MotionEvent.ACTION_DOWN:
                 startX = event.getX();

                 break;
             case MotionEvent.ACTION_MOVE:
                 break;
             case MotionEvent.ACTION_UP:
                 endX = event.getX();
                 float distanceX = startX - endX;
                 float absDistance = Math.abs(distanceX);
                 if(distanceX>0){
                     //左滑
                     if(absDistance>getWidth()/2){
                         position++;
                     }

                 }else{
                     //右滑
                     if(absDistance>getWidth()/2){
                         position--;
                     }

                 }

                 scrollToPosition(position);

                 break;
         }
         return true;
    }

    //滑动到某个位置
    public void scrollToPosition(int position) {
        if(position<0){
            position = 0;
        }
        if(position>getChildCount()-1){
            position = getChildCount()-1;
        }

        //回调接口
        if(onMyViewPagerChangedListener!=null){
            onMyViewPagerChangedListener.onPageChanged(position);
        }

//        scrollTo(position*getWidth(),getScrollY());
        int totalDistanceX = position*getWidth()-getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),totalDistanceX,0);

        invalidate();

    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(scroller.computeScrollOffset()){
            int currX = scroller.getCurrX();
            scrollTo(currX,getScrollY());
            invalidate();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View child = getChildAt(i);
            child.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    private OnMyViewPagerChangedListener onMyViewPagerChangedListener;

    public void setOnMyViewPagerChangedListener(OnMyViewPagerChangedListener onMyViewPagerChangedListener) {
        this.onMyViewPagerChangedListener = onMyViewPagerChangedListener;
    }

    public interface OnMyViewPagerChangedListener{
       void onPageChanged(int position);
    }

}

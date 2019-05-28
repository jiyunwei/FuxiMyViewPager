package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.fuximyviewpager.R;

public class MyCustomView extends View {
    private static final String TAG = "jyw";
    private Bitmap mybg;
    private String name1;
    private String age1;
    private Paint paint;

    public MyCustomView(Context context) {
        this(context,null);
    }

    public MyCustomView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        //通过命名空间获取
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_name");
        String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_age");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_bg");

        Log.d(TAG, "MyCustomView: "+name+age+bg);

        //for循环的形式
        for(int i=0;i<attrs.getAttributeCount();i++){
            String attributeName = attrs.getAttributeName(i);

            String attributeValue = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", attributeName);
            Log.d(TAG, "MyCustomView: "+attributeValue);
        }

        //通过TypeArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);
        name1 = typedArray.getString(R.styleable.MyCustomView_my_name);
        age1 = typedArray.getString(R.styleable.MyCustomView_my_age);
        BitmapDrawable bg1 = (BitmapDrawable) typedArray.getDrawable(R.styleable.MyCustomView_my_bg);
        mybg = bg1.getBitmap();



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(14*getContext().getResources().getDisplayMetrics().density);

        canvas.drawText(name1+age1,50,50,paint);
        canvas.drawBitmap(mybg,100,100,paint);
    }
}

package com.example.fuximyviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.view.MyViewPager;

public class MainActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private int guideImages[] = {R.mipmap.guideone,R.mipmap.guidetwo,R.mipmap.guidethree,R.mipmap.guidefour};
    private RadioGroup rgGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewPager = findViewById(R.id.myviewpager);
        rgGroup = findViewById(R.id.rg_point);
        for(int i=0;i<guideImages.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(guideImages[i]);
            myViewPager.addView(imageView);
        }

        for(int i=0;i<myViewPager.getChildCount();i++){
            RadioButton rb = new RadioButton(this);
            rb.setId(i);
            rgGroup.addView(rb);
        }


        rgGroup.check(0);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               for(int i=0;i<myViewPager.getChildCount();i++){
                   if(checkedId==i){
                       myViewPager.scrollToPosition(checkedId);
                   }
               }
            }
        });

        myViewPager.setOnMyViewPagerChangedListener(new MyViewPager.OnMyViewPagerChangedListener() {
            @Override
            public void onPageChanged(int position) {
                if(rgGroup!=null){
                    rgGroup.check(position);
                }
            }
        });

    }
}

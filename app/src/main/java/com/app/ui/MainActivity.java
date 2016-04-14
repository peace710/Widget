package com.app.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.R;
import com.app.widget.Switch;
import com.app.widget.carousel.CarouselImageView;
import com.app.widget.carousel.CarouselIndicator;

public class MainActivity extends AppCompatActivity implements CarouselImageView.OnCarouselChangeListener{

    private Switch switch1;
    private Switch switch2;

    private CarouselImageView carouselImageView1;
    private CarouselImageView carouselImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch1 = (Switch)findViewById(R.id.switch1);
        switch2 = (Switch)findViewById(R.id.switch2);
        switch2.setSwitch(true);
        switch2.setSwitchOnColor(Color.parseColor("#48FF4081"));

        switch1.setOnSwitchListener(new Switch.OnSwitchListener() {
            @Override
            public void onSwitch(boolean isSwitch) {
                Toast.makeText(MainActivity.this,"==>1:" + isSwitch,Toast.LENGTH_LONG).show();
            }
        });

        switch2.setOnSwitchListener(new Switch.OnSwitchListener() {
            @Override
            public void onSwitch(boolean isSwitch) {
                Toast.makeText(MainActivity.this,"==>2 :" + isSwitch,Toast.LENGTH_LONG).show();
            }
        });

        carouselImageView1 = (CarouselImageView)findViewById(R.id.carouselImageView1);
        carouselImageView2 = (CarouselImageView)findViewById(R.id.carouselImageView2);

        carouselImageView1.addImage(R.drawable.image_1);
        carouselImageView1.addImage(R.drawable.image_2);
        carouselImageView1.addImage(R.drawable.image_3);
        carouselImageView1.addImage(R.drawable.image_4);
        carouselImageView1.addImage(R.drawable.image_5);
        carouselImageView1.addImage(R.drawable.image_6);

        carouselImageView2.setDefaultDrawable(R.drawable.image_6);

        carouselImageView2.addImage("http://ww4.sinaimg.cn/large/7a8aed7bjw1f2tpr3im0mj20f00l6q4o.jpg");
        carouselImageView2.addImage("http://ww1.sinaimg.cn/large/7a8aed7bjw1f2sm0ns82hj20f00l8tb9.jpg");
        carouselImageView2.addImage("http://ww3.sinaimg.cn/large/7a8aed7bjw1f2p0v9vwr5j20b70gswfi.jpg");
        carouselImageView2.addImage("http://ww1.sinaimg.cn/large/7a8aed7bjw1f2nxxvgz7xj20hs0qognd.jpg");
        carouselImageView2.addImage("http://ww2.sinaimg.cn/large/7a8aed7bjw1f2mteyftqqj20jg0siq6g.jpg");
        carouselImageView2.addImage("http://ww2.sinaimg.cn/large/7a8aed7bjw1f2lkx2lhgfj20f00f0dhm.jpg");

        int selectColor = getResources().getColor(R.color.colorPrimary);
        int defaultColor = Color.parseColor("#dadada");

        carouselImageView1.setIndicatorGravity(Gravity.CENTER);
        carouselImageView1.setOnCarouselChangeListener(this);
        carouselImageView1.addCarouselIndicator(new CarouselIndicator.Bulider().setSelectedColor(selectColor).setDefaultColor(defaultColor).setCreateIndicator(new CarouselIndicator.CreateIndicator() {
            @Override
            public View createView() {
                return obtainIndicatorView();
            }
        }).bulid());

        carouselImageView1.startAutoCycle(2000);
        carouselImageView1.setPageTransformer(carouselImageView1.getDefaultTransformer());

        carouselImageView2.setIndicatorGravity(Gravity.CENTER);
        carouselImageView2.setOnCarouselChangeListener(this);
//        carouselImageView2.addCarouselIndicator(new CarouselIndicator.Bulider().setSelectedColor(selectColor).setDefaultColor(defaultColor).setCreateIndicator(new CarouselIndicator.CreateIndicator() {
//            @Override
//            public View createView() {
//                return obtainIndicatorCircleView();
//            }
//        }).bulid());

        Drawable selectDrawable = getResources().getDrawable(R.drawable.icon_3);
        Drawable defaultDrawable = getResources().getDrawable(R.drawable.icon_4);

        carouselImageView2.startAutoCycle(2000);
        carouselImageView2.addCarouselIndicator(new CarouselIndicator.Bulider().setSelectedDrawable(selectDrawable).setDefaultDrawable(defaultDrawable).setCreateIndicator(new CarouselIndicator.CreateIndicator() {
            @Override
            public View createView() {
                return obtainIndicatorCircleView();
            }
        }).bulid());

    }

    private View obtainIndicatorView(){
        LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp2px(5),1);
        View v = new View(this);
        v.setLayoutParams(indicatorParams);
        return  v;
    }

    private View obtainIndicatorCircleView(){
        LinearLayout.LayoutParams indicatorParams = new LinearLayout.LayoutParams(
                dp2px(8),dp2px(8));
        indicatorParams.bottomMargin = dp2px(8);
        indicatorParams.leftMargin = dp2px(8);
        View v = new View(this);
        v.setLayoutParams(indicatorParams);
        return  v;
    }

    private int dp2px(int size){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size,getResources().getDisplayMetrics());
    }

    @Override
    public void onCarouselItemClick(int position) {
        T("click:" + position);
    }


    @Override
    public void onCarouselItemSelected(int position) {

    }

    private void T(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

}

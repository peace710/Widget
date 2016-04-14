package com.app.widget.carousel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kira on 2016/4/13.
 */
public class CarouselImageView  extends FrameLayout implements ViewPager.OnPageChangeListener,View.OnTouchListener,View.OnClickListener{
    private static final String ERROR_EMPTY_URL = "url can not be empty";
    private Context context;
    private ArrayList<ImageView> imageList;
    private ViewPager viewPager;
    private LinearLayout indicatorLayout;
    private OnCarouselChangeListener listener;
    private Drawable defaultDrawable;
    private CarouselImageAdapter adapter;
    private CarouselIndicator carouselIndicator;
    private int currentPosition = 0;
    private Timer timer;
    private TimerTask task;
    private boolean isAutoCycle;
    private int duration = 2000;

    public CarouselImageView(Context context) {
        this(context,null);
    }

    public CarouselImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CarouselImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        context = getContext();
        imageList = new ArrayList<>();
        viewPager = new ViewPager(context);
        indicatorLayout = new LinearLayout(context);
        indicatorLayout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams viewPagerParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(viewPager,viewPagerParams);

        LayoutParams indicatorParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        indicatorParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        addView(indicatorLayout,indicatorParams);

        viewPager.addOnPageChangeListener(this);
        adapter = new CarouselImageAdapter(imageList);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(this);
    }

    public void setPageTransformer(ViewPager.PageTransformer pageTransformer){
        if (null != pageTransformer){
            viewPager.setPageTransformer(true,pageTransformer);
        }
    }

    public ViewPager.PageTransformer getDefaultTransformer(){
        return new DepthPageTransformer();
    }

    public void addImage(String url){
        if (TextUtils.isEmpty(url)){
            throw new NullPointerException("ERROR_EMPTY_URL");
        }
        ImageView image = obtainImageView();
        if (null != defaultDrawable) {
            Glide.with(context).load(url).placeholder(defaultDrawable).centerCrop().into(image);
        }else{
            Glide.with(context).load(url).centerCrop().into(image);
        }
        addImageView(image);
    }

    public void addImage(int resId){
        ImageView image = obtainImageView();
        Glide.with(context).load(resId).centerCrop().into(image);
        addImageView(image);
    }

    private void addImageView(ImageView image){
        imageList.add(image);
        adapter.notifyDataSetChanged();
    }

    private ImageView obtainImageView(){
        ImageView image = new ImageView(context);
        LayoutParams imageParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        image.setLayoutParams(imageParams);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setOnClickListener(this);
        return image;
    }

    public void setDefaultDrawable(int resId){
        setDefaultDrawable(context.getResources().getDrawable(resId));
    }

    public void setDefaultDrawable(Drawable drawable){
        defaultDrawable = drawable;
    }

    public void addCarouselIndicator(CarouselIndicator carouselIndicator){
        this.carouselIndicator = carouselIndicator;
        addCarouselIndicatorViews();
    }

    private void addCarouselIndicatorViews(){
        int size = imageList.size();
        if (null != carouselIndicator) {
            for (int i = 0; i < size; i++) {
                View view = carouselIndicator.getView();
                if (null != view) {
                    indicatorLayout.addView(view);
                    carouselIndicator.addIndicatorView(view);
                }else{
                    indicatorLayout.removeAllViews();
                    carouselIndicator.clearIndicatorView();
                }
            }
            carouselIndicator.resetIndicatorView();
            setCurrentItem(currentPosition);
        }
    }

    public void setIndicatorGravity(int gravity){
        indicatorLayout.setGravity(gravity | Gravity.BOTTOM);
    }

    public synchronized void setCurrentItem(int position){
        setCurrentItem(position,false);
    }

    private void setCurrentItem(int position,boolean isAnim){
        if ( position < imageList.size()) {
            viewPager.setCurrentItem(position,isAnim);
            if (null != carouselIndicator){
                carouselIndicator.setSelected(position);
            }
            currentPosition = position;
        }
    }

    public void startAutoCycle(int duration) {
        isAutoCycle = true;
        if (duration > 1000) {
            this.duration = duration;
        }
        startAutoCycle();
    }

    public void pauseAutoCycle(){
        isAutoCycle = false;
        stopAutoCycle();
    }

    public void resumeAutoCycle(){
        isAutoCycle = true;
        startAutoCycle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != listener) {
            if (null != carouselIndicator){
                carouselIndicator.cancelSelected(currentPosition);
                carouselIndicator.setSelected(position);
            }
            listener.onCarouselItemSelected(position);
        }
        currentPosition = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                startAutoCycle();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (null != listener){
            listener.onCarouselItemClick(currentPosition);
        }
        startAutoCycle();
    }

    public void setOnCarouselChangeListener(OnCarouselChangeListener listener) {
        this.listener = listener;
    }

    public interface OnCarouselChangeListener{
        void onCarouselItemClick(int position);
        void onCarouselItemSelected(int position);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            stopAutoCycle();
        }else if(ev.getAction() == MotionEvent.ACTION_UP) {
            startAutoCycle();
        }
        return super.onInterceptTouchEvent(ev);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int size = imageList.size();
            if (currentPosition == size - 1){
                setCurrentItem(0);
            }else{
                setCurrentItem(currentPosition + 1,true);
            }
        }
    };

    private void startAutoCycle(){
        if (isAutoCycle) {
            if (null == task && null == timer) {
                timer = new Timer();
                task = new AutoCycleTask();
                timer.schedule(task, duration, duration);
            }
        }
    }

    private void stopAutoCycle(){
        if (null != task && null != timer) {
            task.cancel();
            task = null;
            timer.cancel();
            timer = null;
        }
    }

    class AutoCycleTask extends  TimerTask{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    class CarouselImageAdapter extends PagerAdapter{
        private List<ImageView> list;

        public CarouselImageAdapter(List<ImageView> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return null == list ? 0 : list.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = list.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    class DepthPageTransformer  implements ViewPager.PageTransformer{
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            if (position < -1){
                page.setAlpha(0);
            }else if (position <= 0){
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);
            }else if (position <= 1){
                page.setAlpha(1 - position);
                page.setTranslationX(pageWidth * -position);
                float factor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                page.setScaleX(factor);
                page.setScaleY(factor);
            }else{
                page.setAlpha(0);
            }
        }
    }
}
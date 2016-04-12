package com.app.widget.carousel;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
        indicatorParams.gravity = Gravity.BOTTOM;
        addView(indicatorLayout,indicatorParams);

        viewPager.addOnPageChangeListener(this);
        viewPager.setOnTouchListener(this);
        adapter = new CarouselImageAdapter(imageList);
        viewPager.setAdapter(adapter);

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


    public void setCurrentItem(int position){
        if ( position < imageList.size()) {
            viewPager.setCurrentItem(position,true);
            if (null != carouselIndicator){
                carouselIndicator.setSelected(position);
            }
            currentPosition = position;
        }
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
//        if (event.getAction() == MotionEvent.ACTION_UP){
//
//            return true;
//        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (null != listener){
            listener.onCarouselItemClick(currentPosition);
        }
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
//        if (ev.getAction() == MotionEvent.ACTION_DOWN){
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
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

}


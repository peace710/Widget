package com.app.widget.carousel;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Kira on 2016/4/13.
 */
public class CarouselIndicator  {
    private Drawable defaultDrawable;
    private Drawable selectedDrawable;
    private CreateIndicator createIndicator;
    private ArrayList<View> list;

    private CarouselIndicator(){
        list = new ArrayList<>();
    }

    void setSelectedDrawable(Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
    }

    void setDefaultDrawable(Drawable defaultDrawable) {
        this.defaultDrawable = defaultDrawable;
    }

    void setCreateIndicator(CreateIndicator createIndicator) {
        this.createIndicator = createIndicator;
    }

    void addIndicatorView(View v){
        list.add(v);
    }

    void clearIndicatorView(){
        list.clear();
    }

    void resetIndicatorView(){
        if (null != defaultDrawable){
            for (View v : list){
                v.setBackgroundDrawable(defaultDrawable);
            }
        }
    }

    void setSelected(int position){
        if (null != selectedDrawable) {
            list.get(position).setBackgroundDrawable(selectedDrawable);
        }
    }

    void cancelSelected(int position){
        if (null != defaultDrawable) {
            list.get(position).setBackgroundDrawable(defaultDrawable);
        }
    }

    View getView(){
        if (null != createIndicator){
            return createIndicator.createView();
        }
        return null;
    }

    public interface CreateIndicator{
        View createView();
    }

    public static class Bulider{
        private CarouselIndicator carouselIndicator;

        public Bulider(){
            carouselIndicator = new CarouselIndicator();
        }

        public Bulider setDefaultDrawable(Drawable drawable){
            carouselIndicator.setDefaultDrawable(drawable);
            return this;
        }

        public Bulider setSelectedDrawable(Drawable drawable){
            carouselIndicator.setSelectedDrawable(drawable);
            return this;
        }

        public Bulider setDefaultColor(int color){
            carouselIndicator.setDefaultDrawable(new ColorDrawable(color));
            return this;
        }

        public Bulider setSelectedColor(int color){
            carouselIndicator.setSelectedDrawable(new ColorDrawable(color));
            return this;
        }

        public Bulider setCreateIndicator(CreateIndicator createIndicator){
            carouselIndicator.setCreateIndicator(createIndicator);
            return  this;
        }


        public CarouselIndicator bulid(){
            return carouselIndicator;
        }
    }
}

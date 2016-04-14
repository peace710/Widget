package com.app.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Kira on 2016/4/10.
 */
public class Switch extends View implements View.OnClickListener{
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_SPOT_PADDING = 2;
    private static final String DEFAULT_SWITCH_ON_COLOR = "#484EBB7F";
    private static final String DEFAULT_SWITCH_OFF_COLOR = "#DADADA";
    private static final String DEFAULT_SPOT_ON_COLOR = "#B44EBB7F";
    private static final String DEFAULT_SPOT_OFF_COLOR = "#FFFFFF";

    private static final int STATE_SWITCH_OFF = 0x00;
    private static final int STATE_SWITCH_ON_ANIM = STATE_SWITCH_OFF + 1;
    private static final int STATE_SWITCH_ON = STATE_SWITCH_ON_ANIM + 1;
    private static final int STATE_SWITCH_OFF_ANIM = STATE_SWITCH_ON + 1;

    private static final int SWITCH_OFF_POS = 0;
    private static final int SWITCH_ON_POS = 1;
    private int state = STATE_SWITCH_OFF;

    private Resources res;
    private Paint paint;
    private RectF rectF;
    private int switchOnColor;
    private int switchOffColor;
    private int spotOnColor;
    private int spotOffColor;
    private int spotPadding;
    private float pos;
    private boolean isSwitch;

    private OnSwitchListener listener;

    public Switch(Context context) {
        this(context,null);
    }

    public Switch(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Switch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        res = getResources();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        rectF = new RectF();

        TypedArray array = getContext().obtainStyledAttributes(attrs,R.styleable.Switch);
        spotPadding = (int)array.getDimension(R.styleable.Switch_spotPadding,dp2px(DEFAULT_SPOT_PADDING));
        switchOnColor = array.getColor(R.styleable.Switch_switchOnColor,Color.parseColor(DEFAULT_SWITCH_ON_COLOR));
        switchOffColor = array.getColor(R.styleable.Switch_switchOffColor,Color.parseColor(DEFAULT_SWITCH_OFF_COLOR));
        spotOnColor = array.getColor(R.styleable.Switch_spotOnColor,Color.parseColor(DEFAULT_SPOT_ON_COLOR));
        spotOffColor = array.getColor(R.styleable.Switch_spotOffColor,Color.parseColor(DEFAULT_SPOT_OFF_COLOR));
        isSwitch = array.getBoolean(R.styleable.Switch_switchValue,false);
        state = isSwitch ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        array.recycle();

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureSize(dp2px(DEFAULT_WIDTH),widthMeasureSpec);
        int height = measureSize(dp2px(DEFAULT_HEIGHT),heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    private int measureSize(int defaultSize,int spec){
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        if (specMode == MeasureSpec.EXACTLY){
            return specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            return Math.min(defaultSize,specSize);
        }
        return  defaultSize;
    }

    private int dp2px(int size){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size,res.getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (state){
            case STATE_SWITCH_OFF:
                drawSwitchOff(canvas);
                break;
            case STATE_SWITCH_ON_ANIM:
                drawSwitchOnAnim(canvas);
                break;
            case STATE_SWITCH_ON:
                drawSwitchOn(canvas);
                break;
            case STATE_SWITCH_OFF_ANIM:
                drawSwitchOffAnim(canvas);
                break;
        }
    }

    private void drawSwitchOff(Canvas canvas){
        float[] roundRects = calRoundRect(SWITCH_OFF_POS);
        drawRoundRect(canvas,switchOffColor,roundRects[0],roundRects[1],roundRects[2],roundRects[3],roundRects[4]);
        float[] spots = calSpot(SWITCH_OFF_POS);
        drawSpot(canvas,spotOffColor,spots[0],spots[1],spots[2],spots[3],spots[4]);
    }

    private void drawSwitchOnAnim(Canvas canvas){
        float[] roundRects = calRoundRect(SWITCH_OFF_POS);
        drawRoundRect(canvas,switchOnColor,roundRects[0],roundRects[1],roundRects[2],roundRects[3],roundRects[4]);
        roundRects = calRoundRect(pos);
        drawRoundRect(canvas,switchOffColor,roundRects[0],roundRects[1],roundRects[2],roundRects[3],roundRects[4]);
        float[] spots = calSpot(pos);
        drawSpot(canvas,calSpotColor(pos),spots[0],spots[1],spots[2],spots[3],spots[4]);
    }

    private void drawSwitchOn(Canvas canvas){
        float[] roundRects = calRoundRect(SWITCH_OFF_POS);
        drawRoundRect(canvas,switchOnColor,roundRects[0],roundRects[1],roundRects[2],roundRects[3],roundRects[4]);
        float[] spots = calSpot(SWITCH_ON_POS);
        drawSpot(canvas,spotOnColor,spots[0],spots[1],spots[2],spots[3],spots[4]);
    }

    private void drawSwitchOffAnim(Canvas canvas){
        float[] roundRects = calRoundRect(SWITCH_OFF_POS);
        drawRoundRect(canvas,switchOnColor,roundRects[0],roundRects[1],roundRects[2],roundRects[3],roundRects[4]);
        roundRects = calRoundRect(1- pos);
        drawRoundRect(canvas,switchOffColor,roundRects[0],roundRects[1],roundRects[2],roundRects[3],roundRects[4]);
        float[] spots = calSpot(1 - pos);
        drawSpot(canvas,calSpotColor(1 - pos),spots[0],spots[1],spots[2],spots[3],spots[4]);
    }

    private void drawRoundRect(Canvas canvas,int color,float left,float top,float right,float bottom,float r){
        paint.setColor(color);
        rectF.set(left,top,right,bottom);
        canvas.drawRoundRect(rectF,r,r,paint);
    }

    private float[] calRoundRect(float pos){
        int w = getWidth();
        int h = getHeight();
        float left = w * pos;
        float top = h * pos;
        float right = w - left;
        float bottom = h - top;
        float r = (bottom - top) * 0.5f;
        return new float[]{left,top,right,bottom,r};
    }

    private void drawSpot(Canvas canvas,int color,float left,float top,float right,float bottom,float r){
        paint.setColor(color);
        rectF.set(left,top,right,bottom);
        canvas.drawRoundRect(rectF,r,r,paint);
    }

    private float[] calSpot(float pos){
        float r = getHeight() * 0.5f - spotPadding;
        float distance = getWidth() - 2 * r - 2 * spotPadding;
        float left = spotPadding + distance * pos;
        float top = spotPadding;
        float right = left + 2 * r;
        float bottom = top + 2 * r;
        return new float[]{left,top,right,bottom,r};
    }

    private int calSpotColor(float fraction){
        return calColor(fraction,spotOffColor,spotOnColor);
    }

    private int calColor(float fraction,int startColor,int endColor){
        return (Integer)new ArgbEvaluator().evaluate(fraction,startColor,endColor);
    }

    public void setOnSwitchListener(OnSwitchListener listener) {
        this.listener = listener;
    }

    public void setSwitchOnColor(int switchOnColor) {
        this.switchOnColor = switchOnColor;
        invalidate();
    }

    public void setSwitchOffColor(int switchOffColor) {
        this.switchOffColor = switchOffColor;
        invalidate();
    }

    public void setSpotOnColor(int spotOnColor) {
        this.spotOnColor = spotOnColor;
        invalidate();
    }

    public void setSpotOffColor(int spotOffColor) {
        this.spotOffColor = spotOffColor;
        invalidate();
    }

    public void setSpotPadding(int spotPadding) {
        this.spotPadding = dp2px(spotPadding);
        invalidate();
    }

    @Override
    public void onClick(View v) {
        isSwitch = !isSwitch;
        setSwitchAnimState();
        startAnim();
        if (null != listener){
            listener.onSwitch(isSwitch);
        }
    }

    public interface OnSwitchListener{
        void onSwitch(boolean isSwitch);
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean value) {
        isSwitch = value;
        setSwitchState();
        invalidate();
    }

    private void setSwitchState(){
        if (isSwitch){
            state = STATE_SWITCH_ON;
        }else{
            state = STATE_SWITCH_OFF;
        }
    }

    private void setSwitchAnimState(){
        if (isSwitch){
            state = STATE_SWITCH_ON_ANIM;
        }else{
            state = STATE_SWITCH_OFF_ANIM;
        }
    }


    public ValueAnimator startAnim(){
        return startAnim(0,1,500);
    }

    public ValueAnimator startAnim(float start,float end,int duration){
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(start,end);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pos = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if (!valueAnimator.isRunning()){
            valueAnimator.start();
            pos = 0;
        }
        return  valueAnimator;
    }
}

#Android自定义View
Android自定义View时,一般会涉及到onMeasure、onLayout、onSizeChanged、onDraw以及和事件分发传递相关的方法等

###Android自定义View之onMeasure
一般Android在绘制View时会调用onMeasure方法来计算其宽高，当控件的layout_width和layout_height设为match_parent或者wrap_content显示的大小由其父容器控件来决定。若设置为固定大小的值，那么就显示其设定的值

MeasureSpec.getSize()会解析MeasureSpec得到测量值的大小  
MeasureSpec.getMode()会得到3个int类型的值:  
1）MeasureSpec.EXACTLY  
   父容器决定控件的大小，忽略其本身的大小，当width或height设为match_parent或者固定大小时，模式为EXACTLY,控件去占据父容器的剩余空间  
2）MeasureSpec.AT_MOST  
   控件最大可以达到的指定大小，当width或height设置为wrap_content，模式为AT_MOST  
3）MeasureSpec.UNSPECIFIED  
   父容器不对控件施加任何约束，控件可以是任意大小，一般父容器是AdapterView  

   setMeasuredDimension方法用于设置实际控件的大小
```JAVA
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureSize(px2dp(DEFAULT_WIDTH),widthMeasureSpec);
        int height = measureSize(px2dp(DEFAULT_HEIGHT),heightMeasureSpec);
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
```

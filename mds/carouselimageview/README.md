#Switch

###Look
![CarouselImageView](https://github.com/peace710/Widget/blob/master/mds/carouselimageview/1.gif)

### Usage
1) In xml
```xml
    <com.app.widget.carousel.CarouselImageView
        android:layout_width="match_parent"
        android:layout_height="200dip"
        android:id="@+id/carouselImageView"
     />
/>
```

2) In Java
```Java
CarouselImageView
addImage(int resId)  加载本地资源图片
addImage(String url) 加载网络图片
setDefaultDrawable(int resId) 设置轮播图默认图片
setDefaultDrawable(Drawable drawable) 设置轮播图默认图片
addCarouselIndicator(CarouselIndicator carouselIndicator) 加入指示器
setIndicatorGravity(int gravity)  设置指示器显示位置
setCurrentItem(int position)  指定当前选项
startAutoCycle(int duration)  开启轮播
pauseAutoCycle()  暂停轮播
resumeAutoCycle() 恢复轮播
setPageTransformer(PageTransformer pageTransformer)  设置轮播动画
getDefaultTransformer()  获取默认提供轮播动画
setOnCarouselChangeListener(OnCarouselChangeListener listener) 设置轮播的监听


CarouselIndicator.Builder
setDefaultDrawable(Drawable drawable)  设置指示器默认状态图
setSelectedDrawable(Drawable drawable) 设置指示器选中状态图
setDefaultColor(int color)  设置指示器默认颜色
setSelectedColor(int color) 设置指示器默认颜色
setCreateIndicator(CreateIndicator createIndicator) 设置创建指示器的监听
build() 生成指示器
```

3) AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET"></uses-permission>
```

License
=======

    The MIT License (MIT)

	Copyright (c) 2015 Kira

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.











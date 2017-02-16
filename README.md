# ImmersiveSlideMenu
沉浸式状态栏

前段时间做项目 用到了因为SlidingMenu发现SlidingMenu无法实现沉浸式状态栏 当时项目时间紧张 没来的及研究源码 故只改了状态栏的颜色，今天有空在完善项目的时候 感觉只修改状态栏颜色是万万达不到想要的效果 故研究了下SlidingMenu的源码。看了源码才发现 要实现SlidingMenu沉浸式状态栏 只需要把SlidingMenu.Java源码里的一个状态
修改下!
private boolean mActionbarOverlay = false;
修改为:
private boolean mActionbarOverlay = true;

这样 就可以实现沉浸式了！！！

附：加上与状态栏等高的空布局
获取状态栏高度：
/**
 * 获取状态栏高度
 *
 * @param context context
 * @return 状态栏高度
 */
public static int getStatusBarHeight(Context context) {
    int result = -1;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
}

空布局是否显示的一些逻辑：
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    Window window = getWindow();
    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    // 改变titlebar的高度
    int statusbarHeight = BarUtils.getStatusBarHeight(this);
    com.lcworld.intelchargingpile.util.LogUtil.log("statusbarHeight:" + statusbarHeight);
    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_statusBar_slide.getLayoutParams();
    layoutParams.height = statusbarHeight;
    tv_statusBar_slide.setLayoutParams(layoutParams);
    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) tv_statusBar_mian.getLayoutParams();
    layoutParams1.height = statusbarHeight;
    tv_statusBar_mian.setLayoutParams(layoutParams1);
} else {
    tv_statusBar_mian.setVisibility(View.GONE);
    tv_statusBar_slide.setVisibility(View.GONE);
}

实现后的效果图：


/**********************2017.1.12补充*******************/

通过多款手机测试发现  屏幕有软菜单的话布局会以屏幕为ViewGroup，简单点说 底部的布局会显示不完全 被屏幕上的软菜单遮挡 ， 那么单纯改掉这个bool值是不全面的 ，因为slidemenu此时默认用整个屏幕做的布局 ！！！
如这款华为机子底部的button被遮挡在华为的软菜单下边：




在slidemenu.java 中attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) 方法中slideStyle有两种显示方式 
一种是以window展示方式 ，另一种是以屏幕内容展示方式 。actionbarOverlay代表是否覆盖actionbar
 
[java] view plain copy 在CODE上查看代码片派生到我的代码片
if ((slideStyle != 0) && (slideStyle != 1)) {  
           throw new IllegalArgumentException("slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT");  
       }  
  
  
f ((slideStyle != 0) && (slideStyle != 1)) {  
           throw new IllegalArgumentException("slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT");  
       }  



以window展示方式：

[java] view plain copy 在CODE上查看代码片派生到我的代码片
ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();  
ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);  
  
  
decorChild.setBackgroundResource(background);  
decor.removeView(decorChild);  
decor.addView(this);  
setContent(decorChild);  




以屏幕内容展示方式：

[java] view plain copy 在CODE上查看代码片派生到我的代码片
ViewGroup contentParent = (ViewGroup) activity.findViewById(16908290);  
View content = contentParent.getChildAt(0);  
contentParent.removeView(content);  
contentParent.addView(this);  
setContent(content);  
[java] view plain copy 在CODE上查看代码片派生到我的代码片
if (content.getBackground() == null)  
    content.setBackgroundResource(background);  







其中 actionbarOverlay最终决定的是是否要fitSystemWindows

[java] view plain copy 在CODE上查看代码片派生到我的代码片
@SuppressLint({"NewApi"})  
  protected boolean fitSystemWindows(Rect insets) {  
      int leftPadding = insets.left;  
      int rightPadding = insets.right;  
      int topPadding = insets.top;  
      int bottomPadding = insets.bottom;  
      if (!this.mActionbarOverlay) {  
          Log.v("SlidingMenu", "setting padding!");  
          setPadding(leftPadding, topPadding, rightPadding, bottomPadding);  
  
  
      }  
      return true;  
  }  



从源码里可以看到 我们要想做沉浸式状态栏 自然要将mActionbarOverlay设为true   
考虑到 不想被某些手机屏幕上的软菜单遮挡 布局的话  那么就得选用以屏幕内容展示方式
那么 我们可以把attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) 的调用写成attachToActivity(activity, 1, true),那么这个方法是在哪里调用的呢？通过进一步查看源码 
 在SlidingActivityHelper.java中 
[java] view plain copy 在CODE上查看代码片派生到我的代码片
public void onPostCreate(Bundle savedInstanceState) {  
      if ((this.mViewBehind == null) || (this.mViewAbove == null)) {  
          throw new IllegalStateException("Both setBehindContentView must be called in onCreate in addition to setContentView.");  
      }  
  
  
      this.mOnPostCreateCalled = true;  
  
  
      this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1);  
      final boolean secondary;  
      final boolean open;  
      if (savedInstanceState != null) {  
          open = savedInstanceState.getBoolean("SlidingActivityHelper.open");  
          secondary = savedInstanceState.getBoolean("SlidingActivityHelper.secondary");  
      } else {  
          open = false;  
          secondary = false;  
      }  
      new Handler().post(new Runnable() {  
          public void run() {  
              if (open) {  
                  if (secondary)  
                      SlidingActivityHelper.this.mSlidingMenu.showSecondaryMenu(false);  
                  else  
                      SlidingActivityHelper.this.mSlidingMenu.showMenu(false);  
              } else  
                  SlidingActivityHelper.this.mSlidingMenu.showContent(false);  
          }  
      });  
  }  

其中的
 this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1);
改为 ：
mEnableSlide=false;
 this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1, true);
接下来运行项目测试一下 发现大功告成了 ！！！！


[html] view plain copy 在CODE上查看代码片派生到我的代码片
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_1_9457601" name="code" class="java"></pre>  
<p><span style="font-size:18px"></span></p>  
<pre></pre>  
<pre></pre>  
<pre></pre>  
     


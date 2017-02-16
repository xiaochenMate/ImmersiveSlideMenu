<p>
	<span style="font-size:18px"><span style="white-space:pre"></span>前段时间做项目 用到了因为SlidingMenu发现<span style="font-size:18px">SlidingMenu无法实现沉浸式状态栏 当时项目时间紧张 没来的及研究源码 故只改了状态栏的颜色，今天有空在完善项目的时候 感觉只修改状态栏颜色是万万达不到想要的效果 故研究了下<span style="font-size:18px">SlidingMenu的源码。看了源码才发现 要实现<span style="font-size:18px">SlidingMenu沉浸式状态栏 只需要把<span style="color:#ff0000">SlidingMenu.java</span>源码里的一个状态<br />
	修改下!</span></span></span></span>
</p>
<p>
	<span style="font-size:18px"><span style="font-size:18px"><span style="font-size:18px"><span style="font-size:18px"></span></span></span></span>
</p>
<pre style="font-family:宋体; font-size:12pt; background-color:rgb(43,43,43)"><span style="color:rgb(44,204,12)">private boolean </span><span style="color:#ff0000">mActionbarOverlay </span><span style="color:#ffffff">= </span><span style="color:rgb(44,204,12)">false</span><span style="color:rgb(204,120,50)">;</span></pre>
修改为:
<p>
</p>
<p>
	<span style="font-size:18px"><span style="font-size:18px"><span style="font-size:18px"><span style="font-size:18px"></span></span></span></span>
</p>
<pre style="font-family:宋体; font-size:12pt; background-color:rgb(43,43,43)"><span style="color:rgb(44,204,12)">private boolean </span><span style="color:#ff0000">mActionbarOverlay </span><span style="color:#ffffff">= </span><span style="color:rgb(44,204,12)">true</span><span style="color:rgb(204,120,50)">;</span></pre>
<br />

<p>
</p>
<p>
	<span style="font-size:18px">这样 就可以实现沉浸式了！！！</span>
</p>
<p>
	<span style="font-size:18px"><br />
	</span>
</p>
<p>
	<span style="font-size:18px">附：加上与状态栏等高的空布局</span>
</p>
<p>
	<span style="font-size:18px">获取状态栏高度：</span><br />
	
</p>
<p>
	<span style="font-size:18px"></span>
</p>
<pre style="background-color:#2b2b2b; color:#ffffff; font-family:'宋体'; font-size:12.0pt"><span style="color:#971d49"><em>/**
</em></span><span style="color:#971d49"><em> * 获取状态栏高度
</em></span><span style="color:#971d49"><em> *
</em></span><span style="color:#971d49"><em> * </em></span><span style="color:#629755"><strong><em>@param </em></strong></span><span style="color:#8a653b"><em>context </em></span><span style="color:#971d49"><em>context
</em></span><span style="color:#971d49"><em> * </em></span><span style="color:#629755"><strong><em>@return </em></strong></span><span style="color:#971d49"><em>状态栏高度
</em></span><span style="color:#971d49"><em> */
</em></span><span style="color:#2ccc0c">public static int </span><span style="color:#ffc66d">getStatusBarHeight</span>(<span style="color:#a9b7c6">Context </span>context) {
    <span style="color:#2ccc0c">int </span><span style="color:#a9b7c6">result </span>= -<span style="color:#6897bb">1</span><span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#2ccc0c">int </span><span style="color:#a9b7c6">resourceId </span>= context.getResources().getIdentifier(<span style="color:#6a8759">&quot;status_bar_height&quot;</span><span style="color:#cc7832">, </span><span style="color:#6a8759">&quot;dimen&quot;</span><span style="color:#cc7832">, </span><span style="color:#6a8759">&quot;android&quot;</span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#2ccc0c">if </span>(<span style="color:#a9b7c6">resourceId </span>&gt; <span style="color:#6897bb">0</span>) {
        <span style="color:#a9b7c6">result </span>= context.getResources().getDimensionPixelSize(<span style="color:#a9b7c6">resourceId</span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span>}
    <span style="color:#2ccc0c">return </span><span style="color:#a9b7c6">result</span><span style="color:#cc7832">;
</span>}</pre>
<br />

<p>
</p>
<p>
	<span style="font-size:18px">空布局是否显示的一些逻辑：</span>
</p>
<p>
	<span style="font-size:18px"></span>
</p>
<pre style="background-color:#2b2b2b; color:#ffffff; font-family:'宋体'; font-size:12.0pt"><span style="color:#2ccc0c">if </span>(<span style="color:#a9b7c6">Build.VERSION</span>.<span style="color:#9876aa"><em>SDK_INT </em></span>&gt;= <span style="color:#a9b7c6">Build.VERSION_CODES</span>.<span style="color:#9876aa"><em>KITKAT</em></span>) {
    <span style="color:#a9b7c6">Window window </span>= getWindow()<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#a9b7c6">window</span>.setFlags(<span style="color:#a9b7c6">WindowManager.LayoutParams</span>.<span style="color:#9876aa"><em>FLAG_TRANSLUCENT_STATUS</em></span><span style="color:#cc7832">, </span><span style="color:#a9b7c6">WindowManager.LayoutParams</span>.<span style="color:#9876aa"><em>FLAG_TRANSLUCENT_STATUS</em></span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#808080">// 改变titlebar的高度
</span><span style="color:#808080">    </span><span style="color:#2ccc0c">int </span><span style="color:#a9b7c6">statusbarHeight </span>= <span style="color:#a9b7c6">BarUtils</span>.<span style="font-style:italic">getStatusBarHeight</span>(<span style="color:#2ccc0c">this</span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#a9b7c6">com.lcworld.intelchargingpile.util.LogUtil</span>.<span style="font-style:italic">log</span>(<span style="color:#6a8759">&quot;statusbarHeight:&quot; </span>+ <span style="color:#a9b7c6">statusbarHeight</span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#a9b7c6">LinearLayout.LayoutParams layoutParams </span>= (<span style="color:#a9b7c6">LinearLayout.LayoutParams</span>) <span style="color:#9876aa">tv_statusBar_slide</span>.getLayoutParams()<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#a9b7c6">layoutParams</span>.<span style="color:#9876aa">height </span>= <span style="color:#a9b7c6">statusbarHeight</span><span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#9876aa">tv_statusBar_slide</span>.setLayoutParams(<span style="color:#a9b7c6">layoutParams</span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#a9b7c6">RelativeLayout.LayoutParams layoutParams1 </span>= (<span style="color:#a9b7c6">RelativeLayout.LayoutParams</span>) <span style="color:#9876aa">tv_statusBar_mian</span>.getLayoutParams()<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#a9b7c6">layoutParams1</span>.<span style="color:#9876aa">height </span>= <span style="color:#a9b7c6">statusbarHeight</span><span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#9876aa">tv_statusBar_mian</span>.setLayoutParams(<span style="color:#a9b7c6">layoutParams1</span>)<span style="color:#cc7832">;
</span>} <span style="color:#2ccc0c">else </span>{
    <span style="color:#9876aa">tv_statusBar_mian</span>.setVisibility(<span style="color:#a9b7c6">View</span>.<span style="color:#9876aa"><em>GONE</em></span>)<span style="color:#cc7832">;
</span><span style="color:#cc7832">    </span><span style="color:#9876aa">tv_statusBar_slide</span>.setVisibility(<span style="color:#a9b7c6">View</span>.<span style="color:#9876aa"><em>GONE</em></span>)<span style="color:#cc7832">;
</span>}</pre>
<br />

<p>
</p>
<p>
	<span style="font-size:18px">实现后的效果图：</span>
</p>
<p>
	<span style="font-size:18px"><img src="http://img.blog.csdn.net/20170104180518829?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTQ1ODMzOTM0MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" alt="" width="120" height="160" /><br />
	</span>
</p>
<p>
	<span style="font-size:18px"><br />
	</span>
</p>
<p>
	<strong><span style="color:#ff0000">/**********************2017.1.12补充*******************/</span></strong>
</p>
<p>
	<br />
	
</p>
<p>
	通过多款手机测试发现 &nbsp;屏幕有软菜单的话布局会以屏幕为ViewGroup，简单点说 底部的布局会显示不完全 被屏幕上的软菜单遮挡 ， 那么单纯改掉这个bool值是不全面的 ，因为slidemenu此时默认用整个屏幕做的布局 ！！！
</p>
<p>
	如这款华为机子底部的button被遮挡在华为的软菜单下边：
</p>
<p>
	<img src="http://img.blog.csdn.net/20170112121946613?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYTQ1ODMzOTM0MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" width="120" height="160" alt="" />
</p>
<p>
</p>
<div>
	<img src="file:///C:\Users\Administrator\Documents\Tencent Files\23011995\Image\C2C\D1BB9F9F7B409C247B2EEC63BF0B9684.png" alt="" />
</div>
<div>
	<img src="file:///C:\Users\Administrator\Documents\Tencent Files\23011995\Image\C2C\D1BB9F9F7B409C247B2EEC63BF0B9684.png" alt="" />
</div>
<br />

<p>
</p>
<p>
	在slidemenu.java 中attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) 方法中slideStyle有两种显示方式&nbsp;
</p>
<p>
	一种是以window展示方式 ，另一种是以屏幕内容展示方式 。actionbarOverlay代表是否覆盖actionbar
</p>
<p>
	&nbsp;
</p>
<p>
</p>
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_1_276745" name="code" class="java"> if ((slideStyle != 0) &amp;&amp; (slideStyle != 1)) {
            throw new IllegalArgumentException(&quot;slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT&quot;);
        }


if ((slideStyle != 0) &amp;&amp; (slideStyle != 1)) {
            throw new IllegalArgumentException(&quot;slideStyle must be either SLIDING_WINDOW or SLIDING_CONTENT&quot;);
        }</pre>
<br />
<br />

<p>
</p>
<p>
	<br />
	以window展示方式：
</p>
<p>
	<br />
	
</p>
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_2_4337305" name="code" class="java">ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);


decorChild.setBackgroundResource(background);
decor.removeView(decorChild);
decor.addView(this);
setContent(decorChild);</pre>
<br />
<br />
<br />
<br />
以屏幕内容展示方式：
<p>
</p>
<p>
	<br />
	
</p>
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_3_5842994" name="code" class="java">ViewGroup contentParent = (ViewGroup) activity.findViewById(16908290);
View content = contentParent.getChildAt(0);
contentParent.removeView(content);
contentParent.addView(this);
setContent(content);</pre>
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_4_6792276" name="code" class="java">if (content.getBackground() == null)
&nbsp; &nbsp; content.setBackgroundResource(background);
</pre>
<br />
<br />
<br />
<br />
<br />
<br />
<br />
其中 actionbarOverlay最终决定的是是否要fitSystemWindows<br />
<br />

<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_5_2375602" name="code" class="java">  @SuppressLint({&quot;NewApi&quot;})
    protected boolean fitSystemWindows(Rect insets) {
        int leftPadding = insets.left;
        int rightPadding = insets.right;
        int topPadding = insets.top;
        int bottomPadding = insets.bottom;
        if (!this.mActionbarOverlay) {
            Log.v(&quot;SlidingMenu&quot;, &quot;setting padding!&quot;);
            setPadding(leftPadding, topPadding, rightPadding, bottomPadding);


        }
        return true;
    }</pre>
<br />
<br />
<br />
从源码里可以看到 我们要想做沉浸式状态栏 自然要将mActionbarOverlay设为true &nbsp;&nbsp;<br />
考虑到 不想被某些手机屏幕上的软菜单遮挡 布局的话 &nbsp;那么就得选用以屏幕内容展示方式<br />
那么 我们可以把attachToActivity(Activity activity, int slideStyle, boolean actionbarOverlay) 的调用写成attachToActivity(activity, 1, true),那么这个方法是在哪里调用的呢？通过进一步查看源码&nbsp;<br />
&nbsp;在SlidingActivityHelper.java中&nbsp;
<p>
</p>
<p>
</p>
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_6_6563487" name="code" class="java">  public void onPostCreate(Bundle savedInstanceState) {
        if ((this.mViewBehind == null) || (this.mViewAbove == null)) {
            throw new IllegalStateException(&quot;Both setBehindContentView must be called in onCreate in addition to setContentView.&quot;);
        }


        this.mOnPostCreateCalled = true;


        this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1);
        final boolean secondary;
        final boolean open;
        if (savedInstanceState != null) {
            open = savedInstanceState.getBoolean(&quot;SlidingActivityHelper.open&quot;);
            secondary = savedInstanceState.getBoolean(&quot;SlidingActivityHelper.secondary&quot;);
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
    }</pre>
<br />

<p>
</p>
<p>
	其中的<br />
	&nbsp;this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1);<br />
	改为 ：<br />
	mEnableSlide=false;<br />
	&nbsp;this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1, true);<br />
	接下来运行项目测试一下 发现大功告成了 ！！！！<br />
	
</p>
<p>
	<br />
	
</p>
<p>
	<br />
	
</p>
<pre code_snippet_id="2120025" snippet_file_name="blog_20170112_1_9457601" name="code" class="html"><pre code_snippet_id="2120025" snippet_file_name="blog_20170112_1_9457601" name="code" class="java"></pre>
<p>
	<span style="font-size:18px"></span>
</p>
<pre></pre>
<pre></pre>
<pre></pre>
</pre>

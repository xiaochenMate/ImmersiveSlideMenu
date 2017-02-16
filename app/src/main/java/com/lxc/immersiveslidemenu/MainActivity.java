package com.lxc.immersiveslidemenu;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lxc.immersiveslidemenu.slidingmenu.SlidingFragmentActivity;
import com.lxc.immersiveslidemenu.slidingmenu.SlidingMenu;

public class MainActivity extends SlidingFragmentActivity {
    private SlidingMenu slidingMenu;

    @ViewInject(R.id.tv_statusBar_mian)
    private TextView tv_statusBar_mian;
    @ViewInject(R.id.tv_statusBar_slide)
    private TextView tv_statusBar_slide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_main_behind);
        ViewUtils.inject(this);
        boolVersion();
        setSlidiingMenu();
    }


    /**
     * 判断版本
     */
    public void boolVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 改变titlebar的高度
            int statusbarHeight = BarUtils.getStatusBarHeight(this);
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
    }

    /**
     * 初始化侧滑菜单布局
     */
    public void setSlidiingMenu() {
        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);


    }
}

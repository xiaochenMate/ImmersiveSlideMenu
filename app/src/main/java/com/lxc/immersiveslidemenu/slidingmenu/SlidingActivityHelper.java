package com.lxc.immersiveslidemenu.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

public class SlidingActivityHelper {
    private Activity mActivity;
    private SlidingMenu mSlidingMenu;
    private View mViewAbove;
    private View mViewBehind;
    private boolean mBroadcasting = false;

    private boolean mOnPostCreateCalled = false;

    private boolean mEnableSlide = false;

    public SlidingActivityHelper(Activity activity) {
        this.mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        this.mSlidingMenu = new SlidingMenu(this.mActivity);
    }

    public void onPostCreate(Bundle savedInstanceState) {
        if ((this.mViewBehind == null) || (this.mViewAbove == null)) {
            throw new IllegalStateException("Both setBehindContentView must be called in onCreate in addition to setContentView.");
        }

        this.mOnPostCreateCalled = true;

        this.mSlidingMenu.attachToActivity(this.mActivity, this.mEnableSlide ? 0 : 1, true);
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

    public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled) {
        if (this.mOnPostCreateCalled)
            throw new IllegalStateException("enableSlidingActionBar must be called in onCreate.");
        this.mEnableSlide = slidingActionBarEnabled;
    }

    public View findViewById(int id) {
        if (this.mSlidingMenu != null) {
            View v = this.mSlidingMenu.findViewById(id);
            if (v != null)
                return v;
        }
        return null;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("SlidingActivityHelper.open", this.mSlidingMenu.isMenuShowing());
        outState.putBoolean("SlidingActivityHelper.secondary", this.mSlidingMenu.isSecondaryMenuShowing());
    }

    public void registerAboveContentView(View v, ViewGroup.LayoutParams params) {
        if (!this.mBroadcasting)
            this.mViewAbove = v;
    }

    public void setContentView(View v) {
        this.mBroadcasting = true;
        this.mActivity.setContentView(v);
    }

    public void setBehindContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mViewBehind = view;
        this.mSlidingMenu.setMenu(this.mViewBehind);
    }

    public SlidingMenu getSlidingMenu() {
        return this.mSlidingMenu;
    }

    public void toggle() {
        this.mSlidingMenu.toggle();
    }

    public void showContent() {
        this.mSlidingMenu.showContent();
    }

    public void showMenu() {
        this.mSlidingMenu.showMenu();
    }

    public void showSecondaryMenu() {
        this.mSlidingMenu.showSecondaryMenu();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == 4) && (this.mSlidingMenu.isMenuShowing())) {
            showContent();
            return true;
        }
        return false;
    }

}

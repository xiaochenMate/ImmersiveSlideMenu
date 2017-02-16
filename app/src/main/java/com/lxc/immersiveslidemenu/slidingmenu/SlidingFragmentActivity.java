package com.lxc.immersiveslidemenu.slidingmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;


public class SlidingFragmentActivity extends AppCompatActivity implements SlidingActivityBase {
    private SlidingActivityHelper mHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHelper = new SlidingActivityHelper(this);
        this.mHelper.onCreate(savedInstanceState);
    }

    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate(savedInstanceState);
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return this.mHelper.findViewById(id);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mHelper.onSaveInstanceState(outState);
    }

    public void setContentView(int id) {
        setContentView(getLayoutInflater().inflate(id, null));
    }

    public void setContentView(View v) {
        setContentView(v, new ViewGroup.LayoutParams(-1, -1));
    }

    public void setContentView(View v, ViewGroup.LayoutParams params) {
        super.setContentView(v, params);
        this.mHelper.registerAboveContentView(v, params);
    }

    public void setBehindContentView(int id) {
        setBehindContentView(getLayoutInflater().inflate(id, null));
    }

    public void setBehindContentView(View v) {
        setBehindContentView(v, new ViewGroup.LayoutParams(-1, -1));
    }

    public void setBehindContentView(View v, ViewGroup.LayoutParams params) {
        this.mHelper.setBehindContentView(v, params);
    }

    public SlidingMenu getSlidingMenu() {
        return this.mHelper.getSlidingMenu();
    }

    public void toggle() {
        this.mHelper.toggle();
    }

    public void showContent() {
        this.mHelper.showContent();
    }

    public void showMenu() {
        this.mHelper.showMenu();
    }

    public void showSecondaryMenu() {
        this.mHelper.showSecondaryMenu();
    }

    public void setSlidingActionBarEnabled(boolean b) {
        this.mHelper.setSlidingActionBarEnabled(b);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean b = this.mHelper.onKeyUp(keyCode, event);
        if (b)
            return b;
        return super.onKeyUp(keyCode, event);
    }


}

package com.lxc.immersiveslidemenu.slidingmenu;

import android.view.View;
import android.view.ViewGroup;

public interface SlidingActivityBase {
	void setBehindContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams);

	void setBehindContentView(View paramView);

	void setBehindContentView(int paramInt);

	SlidingMenu getSlidingMenu();

	void toggle();

	void showContent();

	void showMenu();

	void showSecondaryMenu();

	void setSlidingActionBarEnabled(boolean paramBoolean);
}

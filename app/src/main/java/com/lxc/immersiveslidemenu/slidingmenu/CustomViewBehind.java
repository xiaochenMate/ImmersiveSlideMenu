package com.lxc.immersiveslidemenu.slidingmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewBehind extends ViewGroup {

//	private static final String TAG = "CustomViewBehind";
//	private static final int MARGIN_THRESHOLD = 48;
	private int mTouchMode = 0;
	private CustomViewAbove mViewAbove;
	private View mContent;
	private View mSecondaryContent;
	private int mMarginThreshold;
	private int mWidthOffset;
	private SlidingMenu.CanvasTransformer mTransformer;
	private boolean mChildrenEnabled;
	private int selectedViewId = 2012;
	private int mMode;
	private boolean mFadeEnabled;
	private final Paint mFadePaint = new Paint();
	private float mScrollScale;
	private Drawable mShadowDrawable;
	private Drawable mSecondaryShadowDrawable;
	private int mShadowWidth;
	private float mFadeDegree;
	private boolean mSelectorEnabled = true;
	private Bitmap mSelectorDrawable;
	private View mSelectedView;

	public CustomViewBehind(Context context) {
		this(context, null);
	}

	public CustomViewBehind(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mMarginThreshold = ((int) TypedValue.applyDimension(1, 48.0F, getResources().getDisplayMetrics()));
	}

	public void setCustomViewAbove(CustomViewAbove customViewAbove) {
		this.mViewAbove = customViewAbove;
	}

	public void setCanvasTransformer(SlidingMenu.CanvasTransformer t) {
		this.mTransformer = t;
	}

	public void setWidthOffset(int i) {
		this.mWidthOffset = i;
		requestLayout();
	}

	public void setMarginThreshold(int marginThreshold) {
		this.mMarginThreshold = marginThreshold;
	}

	public int getMarginThreshold() {
		return this.mMarginThreshold;
	}

	public int getBehindWidth() {
		return this.mContent.getWidth();
	}

	public void setContent(View v) {
		if (this.mContent != null)
			removeView(this.mContent);
		this.mContent = v;
		addView(this.mContent);
	}

	public View getContent() {
		return this.mContent;
	}

	public void setSecondaryContent(View v) {
		if (this.mSecondaryContent != null)
			removeView(this.mSecondaryContent);
		this.mSecondaryContent = v;
		addView(this.mSecondaryContent);
	}

	public View getSecondaryContent() {
		return this.mSecondaryContent;
	}

	public void setChildrenEnabled(boolean enabled) {
		this.mChildrenEnabled = enabled;
	}

	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
		if (this.mTransformer != null)
			invalidate();
	}

	public boolean onInterceptTouchEvent(MotionEvent e) {
		return !this.mChildrenEnabled;
	}

	public boolean onTouchEvent(MotionEvent e) {
		return !this.mChildrenEnabled;
	}

	protected void dispatchDraw(Canvas canvas) {
		if (this.mTransformer != null) {
			canvas.save();
			this.mTransformer.transformCanvas(canvas, this.mViewAbove.getPercentOpen());
			super.dispatchDraw(canvas);
			canvas.restore();
		} else {
			super.dispatchDraw(canvas);
		}
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int width = r - l;
		int height = b - t;
		this.mContent.layout(0, 0, width - this.mWidthOffset, height);
		if (this.mSecondaryContent != null)
			this.mSecondaryContent.layout(0, 0, width - this.mWidthOffset, height);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);
		int contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width - this.mWidthOffset);
		int contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height);
		this.mContent.measure(contentWidth, contentHeight);
		if (this.mSecondaryContent != null)
			this.mSecondaryContent.measure(contentWidth, contentHeight);
	}

	public void setMode(int mode) {
		if ((mode == 0) || (mode == 1)) {
			if (this.mContent != null)
				this.mContent.setVisibility(0);
			if (this.mSecondaryContent != null)
				this.mSecondaryContent.setVisibility(4);
		}
		this.mMode = mode;
	}

	public int getMode() {
		return this.mMode;
	}

	public void setScrollScale(float scrollScale) {
		this.mScrollScale = scrollScale;
	}

	public float getScrollScale() {
		return this.mScrollScale;
	}

	public void setShadowDrawable(Drawable shadow) {
		this.mShadowDrawable = shadow;
		invalidate();
	}

	public void setSecondaryShadowDrawable(Drawable shadow) {
		this.mSecondaryShadowDrawable = shadow;
		invalidate();
	}

	public void setShadowWidth(int width) {
		this.mShadowWidth = width;
		invalidate();
	}

	public void setFadeEnabled(boolean b) {
		this.mFadeEnabled = b;
	}

	public void setFadeDegree(float degree) {
		if ((degree > 1.0F) || (degree < 0.0F))
			throw new IllegalStateException("The BehindFadeDegree must be between 0.0f and 1.0f");
		this.mFadeDegree = degree;
	}

	public int getMenuPage(int page) {
		page = page < 1 ? 0 : page > 1 ? 2 : page;
		if ((this.mMode == 0) && (page > 1))
			return 0;
		if ((this.mMode == 1) && (page < 1)) {
			return 2;
		}
		return page;
	}

	public void scrollBehindTo(View content, int x, int y) {
		int vis = 0;
		if (this.mMode == 0) {
			if (x >= content.getLeft())
				vis = 4;
			scrollTo((int) ((x + getBehindWidth()) * this.mScrollScale), y);
		} else if (this.mMode == 1) {
			if (x <= content.getLeft())
				vis = 4;
			scrollTo((int) (getBehindWidth() - getWidth() + (x - getBehindWidth()) * this.mScrollScale), y);
		} else if (this.mMode == 2) {
			this.mContent.setVisibility(x >= content.getLeft() ? 4 : 0);
			this.mSecondaryContent.setVisibility(x <= content.getLeft() ? 4 : 0);
			vis = x == 0 ? 4 : 0;
			if (x <= content.getLeft())
				scrollTo((int) ((x + getBehindWidth()) * this.mScrollScale), y);
			else {
				scrollTo((int) (getBehindWidth() - getWidth() + (x - getBehindWidth()) * this.mScrollScale), y);
			}
		}
		if (vis == 4)
			Log.v("CustomViewBehind", "behind INVISIBLE");
		setVisibility(vis);
	}

	public int getMenuLeft(View content, int page) {
		if (this.mMode == 0)
			switch (page) {
			case 0:
				return content.getLeft() - getBehindWidth();
			case 2:
				return content.getLeft();
			case 1:
			}
		else if (this.mMode == 1)
			switch (page) {
			case 0:
				return content.getLeft();
			case 2:
				return content.getLeft() + getBehindWidth();
			case 1:
			}
		else if (this.mMode == 2)
			switch (page) {
			case 0:
				return content.getLeft() - getBehindWidth();
			case 2:
				return content.getLeft() + getBehindWidth();
			case 1:
			}
		return content.getLeft();
	}

	public int getAbsLeftBound(View content) {
		if ((this.mMode == 0) || (this.mMode == 2))
			return content.getLeft() - getBehindWidth();
		if (this.mMode == 1) {
			return content.getLeft();
		}
		return 0;
	}

	public int getAbsRightBound(View content) {
		if (this.mMode == 0)
			return content.getLeft();
		if ((this.mMode == 1) || (this.mMode == 2)) {
			return content.getLeft() + getBehindWidth();
		}
		return 0;
	}

	public boolean marginTouchAllowed(View content, int x) {
		int left = content.getLeft();
		int right = content.getRight();
		if (this.mMode == 0)
			return (x >= left) && (x <= this.mMarginThreshold + left);
		if (this.mMode == 1)
			return (x <= right) && (x >= right - this.mMarginThreshold);
		if (this.mMode == 2) {
			return ((x >= left) && (x <= this.mMarginThreshold + left)) || ((x <= right) && (x >= right - this.mMarginThreshold));
		}
		return false;
	}

	public void setTouchMode(int i) {
		this.mTouchMode = i;
	}

	public boolean menuOpenTouchAllowed(View content, int currPage, float x) {
		switch (this.mTouchMode) {
		case 1:
			return true;
		case 0:
			return menuTouchInQuickReturn(content, currPage, x);
		}
		return false;
	}

	public boolean menuTouchInQuickReturn(View content, int currPage, float x) {
		if ((this.mMode == 0) || ((this.mMode == 2) && (currPage == 0)))
			return x >= content.getLeft();
		if ((this.mMode == 1) || ((this.mMode == 2) && (currPage == 2))) {
			return x <= content.getRight();
		}
		return false;
	}

	public boolean menuClosedSlideAllowed(float dx) {
		if (this.mMode == 0)
			return dx > 0.0F;
		if (this.mMode == 1)
			return dx < 0.0F;
		return this.mMode == 2;
	}

	public boolean menuOpenSlideAllowed(float dx) {
		if (this.mMode == 0)
			return dx < 0.0F;
		if (this.mMode == 1)
			return dx > 0.0F;
		return this.mMode == 2;
	}

	public void drawShadow(View content, Canvas canvas) {
		if ((this.mShadowDrawable == null) || (this.mShadowWidth <= 0))
			return;
		int left = 0;
		if (this.mMode == 0) {
			left = content.getLeft() - this.mShadowWidth;
		} else if (this.mMode == 1) {
			left = content.getRight();
		} else if (this.mMode == 2) {
			if (this.mSecondaryShadowDrawable != null) {
				left = content.getRight();
				this.mSecondaryShadowDrawable.setBounds(left, 0, left + this.mShadowWidth, getHeight());
				this.mSecondaryShadowDrawable.draw(canvas);
			}
			left = content.getLeft() - this.mShadowWidth;
		}
		this.mShadowDrawable.setBounds(left, 0, left + this.mShadowWidth, getHeight());
		this.mShadowDrawable.draw(canvas);
	}

	public void drawFade(View content, Canvas canvas, float openPercent) {
		if (!this.mFadeEnabled)
			return;
		int alpha = (int) (this.mFadeDegree * 255.0F * Math.abs(1.0F - openPercent));
		this.mFadePaint.setColor(Color.argb(alpha, 0, 0, 0));
		int left = 0;
		int right = 0;
		if (this.mMode == 0) {
			left = content.getLeft() - getBehindWidth();
			right = content.getLeft();
		} else if (this.mMode == 1) {
			left = content.getRight();
			right = content.getRight() + getBehindWidth();
		} else if (this.mMode == 2) {
			left = content.getLeft() - getBehindWidth();
			right = content.getLeft();
			canvas.drawRect(left, 0.0F, right, getHeight(), this.mFadePaint);
			left = content.getRight();
			right = content.getRight() + getBehindWidth();
		}
		canvas.drawRect(left, 0.0F, right, getHeight(), this.mFadePaint);
	}

	public void drawSelector(View content, Canvas canvas, float openPercent) {
		if (!this.mSelectorEnabled)
			return;
		if ((this.mSelectorDrawable != null) && (this.mSelectedView != null)) {
			String tag = (String) this.mSelectedView.getTag(this.selectedViewId);
			if (tag.equals("CustomViewBehindSelectedView")) {
				canvas.save();

				int offset = (int) (this.mSelectorDrawable.getWidth() * openPercent);
				if (this.mMode == 0) {
					int right = content.getLeft();
					int left = right - offset;
					canvas.clipRect(left, 0, right, getHeight());
					canvas.drawBitmap(this.mSelectorDrawable, left, getSelectorTop(), null);
				} else if (this.mMode == 1) {
					int left = content.getRight();
					int right = left + offset;
					canvas.clipRect(left, 0, right, getHeight());
					canvas.drawBitmap(this.mSelectorDrawable, right - this.mSelectorDrawable.getWidth(), getSelectorTop(), null);
				}
				canvas.restore();
			}
		}
	}

	public void setSelectorEnabled(boolean b) {
		this.mSelectorEnabled = b;
	}

	public void setSelectedView(View v) {
		if (this.mSelectedView != null) {
			this.mSelectedView.setTag(this.selectedViewId, null);
			this.mSelectedView = null;
		}
		if ((v != null) && (v.getParent() != null)) {
			this.mSelectedView = v;
			this.mSelectedView.setTag(this.selectedViewId, "CustomViewBehindSelectedView");
			invalidate();
		}
	}

	private int getSelectorTop() {
		int y = this.mSelectedView.getTop();
		y += (this.mSelectedView.getHeight() - this.mSelectorDrawable.getHeight()) / 2;
		return y;
	}

	public void setSelectorBitmap(Bitmap b) {
		this.mSelectorDrawable = b;
		refreshDrawableState();
	}

}

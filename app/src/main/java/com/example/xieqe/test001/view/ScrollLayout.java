package com.example.xieqe.test001.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollLayout extends ViewGroup {

	private static final String TAG = "ScrollLayout";
	private Scroller mScroller;
	/**滑动速度跟踪类*/
	private VelocityTracker mVelocityTracker;

	private int mCurScreen;
	private int mDefaultScreen = 0;

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;

	private static final int SNAP_VELOCITY = 600;

	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	private float mLastMotionY;
	private OnScreenChangeListener onScreenChangeListener;
	private OnScreenChangeListenerDataLoad onScreenChangeListenerDataLoad;
	private int currentScreenIndex = 0;


	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(context);

		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();//getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件。
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//测量并确定该View的大小，遍历子View，通过measure方法将子View设置为和父View相同的宽高
		Log.e(TAG, "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);

		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {//非精确值
			throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			/*throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");*/
		}

		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurScreen * width, 0);//针对整个父View，将其滑动到指定位置实现滑动分页效果
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childLeft = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				//遍历出每个子View，根据其真实宽度（包括隐藏部分，在onMeasure中已设置为同父View布局时给定的宽高一致），确定安放的位置（依次横向添加，实现横向分页效果）
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	public int getCurrentScreenIndex() {
		return currentScreenIndex;
	}

	public void setCurrentScreenIndex(int currentScreenIndex) {
		this.currentScreenIndex = currentScreenIndex;
	}

	public void snapToDestination() {
		final int screenWidth = getWidth();//返回View可见部分的宽度
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		//根据mScrollX计算需要滑向哪一页， + screenWidth/2的用意：当getScrollX()>=screenWidth/2时，可判断到需要滑向下一页
		snapToScreen(destScreen);
	}

	public void snapToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));//确保传入的参数（当前页码）大于0，小于最大页码
		if (getScrollX() != (whichScreen * getWidth()))//scrollTo滑动的距离 != 宽度*页码：表示需要滑动到匹配页
		{
			final int delta = whichScreen * getWidth() - getScrollX();//计算需要滑动的距离
			mScroller.startScroll(getScrollX(), 0, delta, 0,Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout
			this.setCurrentScreenIndex(whichScreen);
		}
	}

	public void setToScreen(int whichScreen){//功能与snapToScreen相同
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);
	}

	@Override
	public void computeScroll() {
//当parent改变child的mScrollX或mScrollY时触发，典型的触发方式：child通过scroller完成滑动动画，view.invalidate()d调用draw()，draw()调用computeScroll()
		// TODO Auto-generated method stub
		if (mScroller.computeScrollOffset()) {//返回true表示滑动动画还未完成
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				Log.e(TAG, "event down!");
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();//停止滑动动画，移动到最终的x、y位置
				}
				mLastMotionX = x;
				break;

			case MotionEvent.ACTION_MOVE:
				int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;

				scrollBy(deltaX, 0);
				break;

			case MotionEvent.ACTION_UP:

				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000);//初始化速率的单位
				int velocityX = (int) velocityTracker.getXVelocity();//横向速率


				if (velocityX > SNAP_VELOCITY && mCurScreen > 0)
				{//向左滑动速度达到，且当前页码>0；向左滑动
					onScreenChangeListener.onScreenChange(mCurScreen - 1);
					snapToScreen(mCurScreen - 1);
				} else if (velocityX < -SNAP_VELOCITY&& mCurScreen < getChildCount() - 1)
				{//向右滑动速度达到，且当前页码<最大页码；向右滑动
					onScreenChangeListener.onScreenChange(mCurScreen + 1);
					//只往右移动才加载数据
					onScreenChangeListenerDataLoad.onScreenChange(mCurScreen+1);
					snapToScreen(mCurScreen + 1);
				} else
				{
					snapToDestination();//根据滑动距离判断是否滑向下一页
				}

				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
				// }
				mTouchState = TOUCH_STATE_REST;
				break;
			case MotionEvent.ACTION_CANCEL:
				mTouchState = TOUCH_STATE_REST;
				break;
		}

		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = x;
				mLastMotionY = y;
				mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
				break;
			case MotionEvent.ACTION_MOVE:
				final int xDiff = (int) Math.abs(mLastMotionX - x);
				if (xDiff > mTouchSlop) {//x差值大于 判定是否移动的最小距离
					mTouchState = TOUCH_STATE_SCROLLING;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				mTouchState = TOUCH_STATE_REST;
				break;
		}

		return mTouchState != TOUCH_STATE_REST;
		/*如果在滑动状态，返回true,表示已消费（），不再传给viewGroup以下的控件，交给自己的onTouchEvent处理，
后续的ACTION_MOVE--->ACTION_UP不再给onInterceptTouchEvent，直接交给onTouchEvent*/
		/*如果在原始状态，返回false，表示不care，不消费ACTION_DOWN;系统会认为ACTION_DOWN没有发生过，传递给下一层，
后续的ACTION_MOVE--->ACTION_UP继续交给onInterceptTouchEvent分发	*/
	}

	public interface OnScreenChangeListener {
		void onScreenChange(int currentIndex);
	}

	public void setOnScreenChangeListener(
			OnScreenChangeListener onScreenChangeListener) {
		this.onScreenChangeListener = onScreenChangeListener;
	}

	public interface OnScreenChangeListenerDataLoad {
		void onScreenChange(int currentIndex);
	}

	public void setOnScreenChangeListenerDataLoad(OnScreenChangeListenerDataLoad onScreenChangeListenerDataLoad) {
		this.onScreenChangeListenerDataLoad = onScreenChangeListenerDataLoad;
	}
}

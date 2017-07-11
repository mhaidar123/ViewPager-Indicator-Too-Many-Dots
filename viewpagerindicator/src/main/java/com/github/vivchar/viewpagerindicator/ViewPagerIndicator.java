package com.github.vivchar.viewpagerindicator;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/** Created by Mohammad Haidar on 11/07/2017 **/

public class ViewPagerIndicator extends LinearLayoutCompat
{
	private static final float SCALE = 1.6f;
	private static final int NO_SCALE = 1;
	private static final int DEF_VALUE = 10;
	private static final int MAX_SHOW = 5;

	private int mPageCount;
	private int mSelectedIndex;
	private int mItemSize = DEF_VALUE;
	private int mDelimiterSize = DEF_VALUE;
	private ArrayList<View> viewsArrayList = new ArrayList<>();

	@NonNull
	private final List<ImageView> mIndexImages = new ArrayList<>();
	@Nullable
	private ViewPager.OnPageChangeListener mListener;

	public ViewPagerIndicator(@NonNull final Context context) {
		this(context, null);
	}

	public ViewPagerIndicator(@NonNull final Context context, @Nullable final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewPagerIndicator(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		setOrientation(HORIZONTAL);
		final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator, 0, 0);
		try {
			mItemSize = attributes.getDimensionPixelSize(R.styleable.ViewPagerIndicator_itemSize, DEF_VALUE);
			mDelimiterSize = attributes.getDimensionPixelSize(R.styleable.ViewPagerIndicator_delimiterSize, DEF_VALUE);
		} finally {
			attributes.recycle();
		}
		if (isInEditMode()) {
			createEditModeLayout();
		}
	}

	private void createEditModeLayout()
	{
		for (int i = 0; i < 5; ++i) {
			final FrameLayout boxedItem = createBoxedItem(i);
			addView(boxedItem);
			if (i == 1) {
				final View item = boxedItem.getChildAt(0);
				final ViewGroup.LayoutParams layoutParams = item.getLayoutParams();
				layoutParams.height *= SCALE;
				layoutParams.width *= SCALE;
				item.setLayoutParams(layoutParams);
			}
		}
	}

	public
	void setupWithViewPager(@NonNull final ViewPager viewPager)
	{
		setPageCount(viewPager.getAdapter().getCount());
		viewPager.addOnPageChangeListener(new OnPageChangeListener());
	}

	public void addOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
		mListener = listener;
	}

	private void setSelectedIndex(final int selectedIndex)
	{
		if (selectedIndex < 0 || selectedIndex > mPageCount - 1) {
			return;
		}


		if(selectedIndex>mSelectedIndex)
		{
			if(selectedIndex>2 && selectedIndex<mPageCount-2)
			{
				viewsArrayList.add(getChildAt(2+selectedIndex));
				viewsArrayList.remove(getChildAt(selectedIndex-3));


				getChildAt(2+selectedIndex).animate().alphaBy(1.0f).setDuration(0).setListener(new Animator.AnimatorListener()
				{

					@Override
					public void onAnimationStart(Animator animation)
					{

					}

					@Override
					public void onAnimationEnd(Animator animation)
					{
						getChildAt(2+selectedIndex).setVisibility(View.VISIBLE);
						getChildAt(2+selectedIndex).setAlpha(0.4f);
						getChildAt(1+selectedIndex).setAlpha(1.0f);
					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				}).start();


				getChildAt(selectedIndex-3).animate().alpha(0.0f).setDuration(200).setListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						getChildAt(selectedIndex-3).setVisibility(View.GONE);
						getChildAt(selectedIndex-2).setAlpha(0.4f);
					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				}).start();
			}
			else if(selectedIndex==mPageCount-2)
			{
				getChildAt(mPageCount-1).setAlpha(1.0f);
			}

			/*else if(selectedIndex>2)
			{
				viewsArrayList.get(viewsArrayList.size()-1).setAlpha(0.4f);
				getChildAt(0).setAlpha(1.0f);
			}
			else if(selectedIndex>mPageCount-3)
			{
				viewsArrayList.get(0).setAlpha(0.4f);
				getChildAt(mPageCount-1).setAlpha(1.0f);
			}
			else
			{
				viewsArrayList.get(0).setAlpha(0.4f);
				getChildAt(mPageCount-1).setAlpha(1.0f);
				getChildAt(0).setAlpha(1.0f);

			}*/
		}
		else
		{
			if(selectedIndex<mPageCount-3 && selectedIndex>1)
			{
				getChildAt(3+selectedIndex).animate().alphaBy(0.0f).setDuration(200).setListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						getChildAt(3+selectedIndex).setVisibility(View.GONE);
						getChildAt(2+selectedIndex).setAlpha(0.4f);
					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				}).start();

				getChildAt(selectedIndex-2).animate().alphaBy(1.0f).setDuration(0).setListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						getChildAt(selectedIndex-2).setVisibility(View.VISIBLE);
						getChildAt(selectedIndex-2).setAlpha(0.4f);
						getChildAt(selectedIndex-1).setAlpha(1.0f);
					}

					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				}).start();

				viewsArrayList.remove(getChildAt(3+selectedIndex));
				viewsArrayList.add(getChildAt(selectedIndex-2));

			}
			else if(selectedIndex==1)
			{
				getChildAt(0).setAlpha(1.0f);
			}
			/*else if(selectedIndex>mPageCount-3)
			{
				viewsArrayList.get(0).setAlpha(0.4f);
				getChildAt(mPageCount-1).setAlpha(1.0f);
			}
			else if(selectedIndex<=2)
			{
				viewsArrayList.get(viewsArrayList.size()-1).setAlpha(0.4f);
				getChildAt(0).setAlpha(1.0f);
			}
			else
			{
				viewsArrayList.get(viewsArrayList.size()-1).setAlpha(0.4f);
				getChildAt(0).setAlpha(1.0f);
				getChildAt(mPageCount-1).setAlpha(1.0f);
			}*/

		}

		final ImageView unselectedView = mIndexImages.get(mSelectedIndex);
		unselectedView.setImageResource(R.drawable.white_circle);
		unselectedView.animate().scaleX(NO_SCALE).scaleY(NO_SCALE).setDuration(300).start();

		final ImageView selectedView = mIndexImages.get(selectedIndex);
		selectedView.setImageResource(R.drawable.blue_circle);
		selectedView.animate().scaleX(SCALE).scaleY(SCALE).setDuration(300).start();

		mSelectedIndex = selectedIndex;
	}

	private void setPageCount(final int pageCount)
	{
		mPageCount = pageCount;
		mSelectedIndex = 0;
		removeAllViews();
		mIndexImages.clear();

		for (int i = 0; i < pageCount; ++i)
		{
			addView(createBoxedItem(i));
			if(i>MAX_SHOW-1)
				getChildAt(i).setVisibility(View.GONE);

			else
			{
				viewsArrayList.add(getChildAt(i));
				if(i==MAX_SHOW-1)
					viewsArrayList.get(i).setAlpha(0.4f);
			}

		}
		setSelectedIndex(mSelectedIndex);
	}

	@NonNull
	private FrameLayout createBoxedItem(final int position)
	{
		final FrameLayout box = new FrameLayout(getContext());
		final ImageView item = createItem();
		box.addView(item);
		mIndexImages.add(item);

		final LinearLayoutCompat.LayoutParams boxParams = new LinearLayoutCompat.LayoutParams((int) (mItemSize * SCALE), (int) (mItemSize * SCALE));
		if (position > 0)
		{
			boxParams.setMargins(mDelimiterSize, 0, 0, 0);
		}
		box.setLayoutParams(boxParams);
		return box;
	}

	@NonNull
	private ImageView createItem()
	{
		final ImageView index = new ImageView(getContext());
		final FrameLayout.LayoutParams indexParams = new FrameLayout.LayoutParams(mItemSize, mItemSize);
		indexParams.gravity = Gravity.CENTER;
		index.setLayoutParams(indexParams);
		index.setImageResource(R.drawable.white_circle);
		index.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return index;
	}

	private class OnPageChangeListener implements ViewPager.OnPageChangeListener
	{
		@Override
		public
		void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
			if (mListener != null) {
				mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public
		void onPageSelected(final int position) {
			setSelectedIndex(position);
			if (mListener != null) {
				mListener.onPageSelected(position);
			}
		}

		@Override
		public
		void onPageScrollStateChanged(final int state) {
			if (mListener != null) {
				mListener.onPageScrollStateChanged(state);
			}
		}
	}

}

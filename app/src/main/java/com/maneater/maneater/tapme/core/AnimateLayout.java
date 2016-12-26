package com.maneater.maneater.tapme.core;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.maneater.maneater.tapme.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class AnimateLayout extends FrameLayout implements View.OnClickListener {

    private final int ANIMATOR_ID = 0x7f0b0000;
    private final int ANIMATOR_LISTENER_ID = 0x7f0b0001;

    //每次最少
    final private int perSizeMin = 1;
    //每次最多
    final private int perSizeMax = 3;
    //增加控件的最大时间间隔
    final private int perCreateMaxDelay = 500;
    //增加控件的最小时间间隔
    final private int perCreateMinDelay = 400;
    //下落时长
    final private int perChildFallDuration = 8000;
    //默认图片
    final private int mImageViewDrawable = R.mipmap.ic_launcher;


    public AnimateLayout(Context context) {
        this(context, null);
    }

    public AnimateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isFinished) {
            return;
        }
        removeCallbacks(createChildRunnable);
        postDelayed(createChildRunnable, 50);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(createChildRunnable);
    }


    private int mPreCreateSize = 0;

    //TODO
    private SparseArrayCompat<AnimateChild<?>> animateCache = new SparseArrayCompat<>();


    private Runnable createChildRunnable = new Runnable() {
        @Override
        public void run() {
            if (mDelegateNeedCount <= 0 || mHasChildDown) {
                return;
            }

            if (getWidth() > 0 && getHeight() > 0) {
                int createSizeSeed = perSizeMin;
                if (mPreCreateSize == perSizeMin) {
                    createSizeSeed = Math.min(createSizeSeed + 1, perSizeMax);
                }
                int createSize = (int) (createSizeSeed + (perSizeMax - createSizeSeed + 0.5) * Math.random());

                List<Range<Integer>> ranges = new ArrayList<>();
                ranges.add(new Range<>(0, getWidth()));
                for (int i = 0; i < createSize; i++) {
                    addChildView(i, ranges);
                }
                mPreCreateSize = createSize;
                postDelayed(this, (long) (perCreateMinDelay + Math.random() * (perCreateMaxDelay - perCreateMinDelay + 0.5f)));
            } else {
                postDelayed(this, 50);
            }
        }
    };

    private AnimateDelegate animateDelegate = null;
    private int mDelegateNeedCount = 0;
    private int mNowAnimateIndex = 0;
    /*是否有任意子控件触底，触底之后不再创建新的控件**/
    private boolean mHasChildDown = false;

    public void setAnimateDelegate(AnimateDelegate animateAdapter) {
        this.animateDelegate = animateAdapter;
        this.mDelegateNeedCount = animateDelegate != null ? animateDelegate.getCount() : 0;
        this.mNowAnimateIndex = 0;
        //TODO remove all
    }

    /**
     * @param index
     * @return 可在这里返回任意View
     */
    protected View createChildView(int index) {
        AnimateChild<?> animateChild = null;
        if (animateDelegate != null) {
            animateChild = animateCache.get(index);

            if (animateChild == null) {
                animateChild = animateDelegate.onCreate(LayoutInflater.from(getContext()), index, mNowAnimateIndex);
            }
            if (animateChild != null && animateChild.animateView != null) {
                animateDelegate.onBind(animateChild, animateChild.animateView, index, mNowAnimateIndex);
                return animateChild.animateView;
            }
        }
        return null;
    }

    private View addChildView(int index, List<Range<Integer>> sourceRanges) {
        final View childView = createChildView(index);
        if (childView == null) {
            return null;
        }

        LayoutParams layoutParams = generateDefaultLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        childView.measure(0, 0);
        int measuredHeight = childView.getMeasuredHeight();
        int measuredWidth = childView.getMeasuredWidth();

        layoutParams.leftMargin = Helper.findRange(getWidth(), measuredWidth, sourceRanges).getLower();
        layoutParams.topMargin = -measuredHeight;
        addView(childView, layoutParams);
        mNowAnimateIndex++;
        mNowAnimateIndex = mNowAnimateIndex % mDelegateNeedCount;
        childView.setRotation((float) (Math.random() * 45) * (Math.random() > 0.5f ? 1 : -1));

        final ValueAnimator animator = ValueAnimator.ofFloat((float) (getHeight() + measuredHeight * 1.5));
        final ChildAnimatorListener childAnimatorListener = new ChildAnimatorListener(childView);
        childView.setTag(ANIMATOR_ID, animator);
        childView.setTag(ANIMATOR_LISTENER_ID, childAnimatorListener);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(perChildFallDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewCompat.setTranslationY(childView, (Float) animation.getAnimatedValue());
            }
        });
        animator.addListener(childAnimatorListener);
        animator.start();

        childView.setOnClickListener(AnimateLayout.this);
        return childView;
    }

    private class ChildAnimatorListener implements Animator.AnimatorListener {

        private WeakReference<View> targetView = null;

        public ChildAnimatorListener(View targetView) {
            this.targetView = new WeakReference<View>(targetView);
        }

        private boolean finishWhenRepeat = false;

        public void setFinishWhenRepeat(boolean finishWhenRepeat) {
            this.finishWhenRepeat = finishWhenRepeat;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            mHasChildDown = true;
            if (finishWhenRepeat) {
                animation.cancel();
                removeView(targetView.get());
            }
        }
    }


    private boolean isFinished = false;

    private View clickView = null;


    public void stopAnimateChild() {
        isFinished = true;
        removeCallbacks(createChildRunnable);
        finishChildAnimator();
    }

    @Override
    public void onClick(final View view) {
        if (isFinished) {
            return;
        }
        isFinished = true;
        clickView = view;
        invalidate();

        finishChildAnimator();

        removeCallbacks(createChildRunnable);
        Animator animator = (Animator) view.getTag(ANIMATOR_ID);
        if (animator != null) {
            animator.cancel();
        }


        int transX = (int) ViewCompat.getTranslationX(view);
        int transY = (int) ViewCompat.getTranslationY(view);

        int left = view.getLeft();
        int top = view.getTop();

        int parentWidth = getWidth();
        int parentHeight = getHeight();

        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();

        int finalTranX = (parentWidth - viewWidth) / 2;
        view.animate()
                .translationX(finalTranX - left)
                .translationY((parentHeight - viewHeight) / 2)
                .scaleX(3).scaleY(3).rotationY(90 * 3).rotation(0)
                .setDuration(400).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
//                    viewGroup.getChildAt(0).setVisibility(INVISIBLE);
                    viewGroup.getChildAt(1).setVisibility(VISIBLE);
                    view.animate().rotationY(90 * 4).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200).
                            start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                view.animate().setListener(null);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void finishChildAnimator() {
        int childSize = getChildCount();
        for (int i = 0; i < childSize; i++) {
            View view = getChildAt(i);
            ChildAnimatorListener listener = (ChildAnimatorListener) view.getTag(ANIMATOR_LISTENER_ID);
            listener.setFinishWhenRepeat(true);
        }
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (clickView != null) {
            int index = indexOfChild(clickView);
            if (index >= 0) {
                if (i == childCount - 1) {
                    return index;
                } else if (i == index) {
                    return childCount - 1;
                }
            }
        }
        return super.getChildDrawingOrder(childCount, i);
    }
}

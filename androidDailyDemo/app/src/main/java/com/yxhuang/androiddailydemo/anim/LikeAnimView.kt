package com.yxhuang.androiddailydemo.anim

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.yxhuang.androiddailydemo.R
import kotlin.math.min

/**
 * Created by yxhuang
 * Date: 2021/7/14
 * Description:
 *  贝塞尔曲线动画
 */
class LikeAnimView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val SCALE_ANIM_DURATION = 500L
        private const val TOTAL_ANIM_DURATION = 10_000L
    }

    private var mHeight = 0
    private var mWidth = 0

    private var mDrawables = ArrayList<Drawable>()

    fun addDrawables(drawables: List<Drawable>) {
        mDrawables.clear()
        mDrawables.addAll(drawables)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        mWidth = width
        mHeight = height
    }


    fun addFloatView() {
        val imageView = ImageView(context)
        //随机显示点赞icon
        val pos = (Math.random() * mDrawables.count()).toInt()
        imageView.setImageDrawable(mDrawables[pos])

        val size = (min(mWidth, mHeight) * 0.1).toInt()
        imageView.layoutParams = LayoutParams(size, size)
        addView(imageView, 0)

        setAnim(imageView).start()
        createBezierAnimator(imageView).start()
    }

    /**
     * 缩放使过渡顺畅
     */
    private fun setAnim(view: View): AnimatorSet {
        val scaleX: ObjectAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 1f)
        val scaleY: ObjectAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 1f)
        val enter = AnimatorSet()
        enter.duration = SCALE_ANIM_DURATION
        enter.interpolator = LinearInterpolator()
        enter.playTogether(scaleX, scaleY)
        enter.setTarget(view)
        return enter
    }

    private fun createBezierAnimator(target: View): ValueAnimator {
        val evaluator = BezierEvaluator()
        val bezierListener = BezierAnimatorListener(target)
        val animator = ValueAnimator.ofObject(evaluator, evaluator.bezierPoints.p0, evaluator.bezierPoints.p3)
        animator.addUpdateListener(bezierListener)
        animator.addListener(bezierListener)
        animator.setTarget(target)
        animator.duration = TOTAL_ANIM_DURATION
        return animator
    }


    private inner class BezierAnimatorListener(private val target: View) : Animator.AnimatorListener,
            ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationEnd(animation: Animator?) {
            removeView(target)
        }

        override fun onAnimationUpdate(animation: ValueAnimator) {
            val pointF = animation.animatedValue as PointF
            target.x = pointF.x
            target.y = pointF.y
            target.alpha = 1 - animation.animatedFraction
        }

        override fun onAnimationStart(animation: Animator?) = Unit
        override fun onAnimationRepeat(animation: Animator?) = Unit
        override fun onAnimationCancel(animation: Animator?) = Unit
    }

    private data class BezierPoint(val p0: PointF, val p1: PointF, val p2: PointF, val p3: PointF)


    private inner class BezierEvaluator : TypeEvaluator<PointF> {

        val bezierPoints: BezierPoint by lazy {
            val p0 = PointF((mWidth / 4).toFloat(), (mHeight).toFloat())

            val p1 = PointF((Math.random() * mWidth).toFloat() - mWidth / 4, -(Math.random() * mHeight + mHeight * 2.0).toFloat())
            val p2 = PointF((Math.random() * mWidth).toFloat() - mWidth / 4, -(Math.random() * mHeight + mHeight * 3.0).toFloat())
            val p3 = PointF((Math.random() * mWidth).toFloat() - mWidth / 4, -(Math.random() * mHeight + mHeight * 4.0).toFloat())

            BezierPoint(p0, p1, p2, p3)
        }

        override fun evaluate(time: Float, startValue: PointF, endValue: PointF): PointF {
            val timeLeft = 1 - time
            val point = PointF()

            /**
             *  贝塞尔曲线三次方公式：p0、p1、p2、p3四个点在平面或在三维空间中定义了三次方贝兹曲线。
             *  B(t) = (1-t)^3 * t^0 * p0
             *      + (1-t)^2 * t^1 * p1 * 3
             *      + (1-t)^1 * t^2 * p2 * 3
             *      + (1-t)^0 * t^3 * p3
             */
            point.x = (timeLeft * timeLeft * timeLeft * bezierPoints.p0.x
                    + timeLeft * timeLeft * time * bezierPoints.p1.x * 3
                    + timeLeft * time * time * bezierPoints.p2.x * 3
                    + time * time * time * bezierPoints.p3.x)

            point.y = (timeLeft * timeLeft * timeLeft * bezierPoints.p0.y
                    + timeLeft * timeLeft * time * bezierPoints.p1.y * 3
                    + timeLeft * time * time * bezierPoints.p2.y * 3
                    + time * time * time * bezierPoints.p3.y)

            return point
        }
    }


}


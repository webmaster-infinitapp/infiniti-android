package com.payproapp.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import com.payproapp.R
import com.payproapp.extensions.getDimensionInPx

/**
 * It represents a set of indicator dots
 * can be used to indicate the current length of the input
 *
 *
 * Created by Eloy
 */
class IndicatorDots @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0) : LinearLayoutCompat(context, attrs,
                                                                                          defStyleAttr) {

    private var mDotDiameter: Int = 0
    private var mDotSpacing: Int = 0
    private var mFillDrawable: Int = 0
    private var mEmptyDrawable: Int = 0
    private var mPinLength: Int = 0
    private var mPreviousLength: Int = 0

    var pinLength: Int
        get() = mPinLength
        set(pinLength) {
            this.mPinLength = pinLength
            removeAllViews()
            initView(context)
        }


    companion object {
        const val DEFAULT_PIN_LENGTH = 4
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorDots)

        try {
            mDotDiameter = typedArray.getDimension(R.styleable.IndicatorDots_dotDiameter,
                                                   getContext().getDimensionInPx(R.dimen.default_dot_diameter)).toInt()
            mDotSpacing = typedArray.getDimension(R.styleable.IndicatorDots_dotSpacing,
                                                  getContext().getDimensionInPx(R.dimen.default_dot_spacing)).toInt()
            mFillDrawable = typedArray.getResourceId(R.styleable.IndicatorDots_dotFilledBackground,
                                                     R.drawable.dot_filled)
            mEmptyDrawable = typedArray.getResourceId(R.styleable.IndicatorDots_dotEmptyBackground,
                                                      R.drawable.dot_empty)
            mPinLength = typedArray.getInt(R.styleable.IndicatorDots_pinLength, DEFAULT_PIN_LENGTH)
        } finally {
            typedArray.recycle()
        }

        initView(context)
    }

    private fun initView(context: Context) {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR)
        for (i in 0 until mPinLength) {
            val dot = View(context)
            emptyDot(dot)
            val params = LinearLayoutCompat.LayoutParams(mDotDiameter, mDotDiameter)
            params.setMargins(mDotSpacing, 0, mDotSpacing, 0)
            dot.layoutParams = params
            addView(dot)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // If the indicator type is not fixed
        val params = this.layoutParams
        params.height = mDotDiameter
        requestLayout()
    }

    internal fun updateDot(length: Int) {
        mPreviousLength = if (length > 0) {
            if (length > mPreviousLength) {
                val dot = getChildAt(length - 1)
                dot?.let {
                    fillDot(it)
                }
            } else {
                val dot = getChildAt(length)
                dot?.let {
                    emptyDot(it)
                }
            }
            length
        } else {
            // When {@code mPinLength} is 0, we need to reset all the views back to empty
            for (i in 0 until childCount) {
                val v = getChildAt(i)
                emptyDot(v)
            }
            0
        }
    }

    private fun emptyDot(dot: View) {
        dot.setBackgroundResource(mEmptyDrawable)
    }

    private fun fillDot(dot: View) {
        dot.setBackgroundResource(mFillDrawable)
    }

}
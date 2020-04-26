package com.smile.zqview

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.max

class SmTextView : AppCompatTextView {

    private var autoRound = false
    private var roundRadius = 10F
    private var bgColor = Color.TRANSPARENT
    private var bgColorSel = Color.TRANSPARENT

    //渐变
    private var gradient = false
    private var gradientOrientation = 0
    private var bgColorFrom = Color.TRANSPARENT
    private var bgColorTo = Color.TRANSPARENT

    private lateinit var stateListDrawable: StateListDrawable

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initViews(attrs)
    }

    private fun initViews(attrs: AttributeSet?) {
        attrs?.let {

            val a = context.obtainStyledAttributes(attrs, R.styleable.SmTextView)

            autoRound = a.getBoolean(R.styleable.SmTextView_sm_autoRound, false)
            bgColor = a.getColor(R.styleable.SmTextView_sm_bgColor, Color.TRANSPARENT)
            roundRadius = a.getDimension(R.styleable.SmTextView_sm_roundRadius, 0F)

            //渐变
            gradient = a.getBoolean(R.styleable.SmTextView_sm_gradient, false)
            bgColorFrom = a.getColor(R.styleable.SmTextView_sm_bgColorFrom, Color.TRANSPARENT)
            bgColorTo = a.getColor(R.styleable.SmTextView_sm_bgColorTo, Color.TRANSPARENT)
            gradientOrientation = a.getInt(R.styleable.SmTextView_sm_gradient_orientation, 0)

            a.recycle()
        }


        stateListDrawable = if (background is StateListDrawable) {
            background as StateListDrawable
        } else {
            StateListDrawable()
        }


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBg()
    }


    private fun setBg() {

        val bg = GradientDrawable()

        if (autoRound) {
            bg.cornerRadius = max(measuredWidth, measuredHeight).toFloat() / 2F
        } else {
            bg.cornerRadius = roundRadius
        }

        if (gradient) {
            bg.colors = intArrayOf(bgColorFrom, bgColorTo)
            bg.orientation = getOrientation(gradientOrientation)
        } else {
            bg.setColor(bgColor)
        }

        background = bg
    }

    private fun getOrientation(o: Int): GradientDrawable.Orientation {
        return when (o) {
            0 -> GradientDrawable.Orientation.TOP_BOTTOM
            1 -> GradientDrawable.Orientation.TR_BL
            2 -> GradientDrawable.Orientation.RIGHT_LEFT
            3 -> GradientDrawable.Orientation.BR_TL
            4 -> GradientDrawable.Orientation.BOTTOM_TOP
            5 -> GradientDrawable.Orientation.BL_TR
            6 -> GradientDrawable.Orientation.LEFT_RIGHT
            7 -> GradientDrawable.Orientation.TL_BR
            else -> GradientDrawable.Orientation.LEFT_RIGHT
        }
    }

    private fun dp2px(dpValue: Float): Int {
        val scale =
            Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}
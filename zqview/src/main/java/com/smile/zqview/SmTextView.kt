package com.smile.zqview

import android.content.Context
import android.content.res.ColorStateList
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
    private var bgColorFromSel = Color.TRANSPARENT
    private var bgColorTo = Color.TRANSPARENT
    private var bgColorToSel = Color.TRANSPARENT

    //文本
    private var txtColor = Color.BLACK
    private var txtColorSel = Color.BLACK

    //边框
    private var strokeColor = 0
    private var strokeWidth = 0F

    private lateinit var bgStateListDrawable: StateListDrawable
    private lateinit var textColorStateList: ColorStateList

    private val STATE_ENABLE = intArrayOf(android.R.attr.state_enabled)
    private val STATE_UN_ENABLE = intArrayOf(-android.R.attr.state_selected)

    private val STATE_NORMAL = intArrayOf(-android.R.attr.state_selected)
    private val STATE_SELECT = intArrayOf(android.R.attr.state_selected)

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


        textColorStateList = textColors
        val colorForState = textColorStateList.getColorForState(STATE_ENABLE, txtColor)

        attrs?.let {

            val a = context.obtainStyledAttributes(attrs, R.styleable.SmTextView)

            autoRound = a.getBoolean(R.styleable.SmTextView_sm_autoRound, false)
            bgColor = a.getColor(R.styleable.SmTextView_sm_bgColor, Color.TRANSPARENT)
            bgColorSel = a.getColor(R.styleable.SmTextView_sm_bgColor_sel, bgColor)
            roundRadius = a.getDimension(R.styleable.SmTextView_sm_roundRadius, 0F)

            //渐变
            gradient = a.getBoolean(R.styleable.SmTextView_sm_gradient, false)
            bgColorFrom = a.getColor(R.styleable.SmTextView_sm_bgColorFrom, Color.TRANSPARENT)
            bgColorFromSel = a.getColor(R.styleable.SmTextView_sm_bgColorFromSel, bgColorFrom)
            bgColorTo = a.getColor(R.styleable.SmTextView_sm_bgColorTo, Color.TRANSPARENT)
            bgColorToSel = a.getColor(R.styleable.SmTextView_sm_bgColorToSel, bgColorTo)
            gradientOrientation = a.getInt(R.styleable.SmTextView_sm_gradient_orientation, 0)

            //文本color
            txtColor = a.getColor(R.styleable.SmTextView_sm_textColor, colorForState)
            txtColorSel = a.getColor(R.styleable.SmTextView_sm_textColorSel, txtColor)

            //stroke
            strokeColor = a.getColor(R.styleable.SmTextView_sm_stroke_color, Color.TRANSPARENT)
            strokeWidth = a.getDimension(R.styleable.SmTextView_sm_stroke_width, 0F)

            a.recycle()
        }

        bgStateListDrawable = if (background is StateListDrawable) {
            background as StateListDrawable
        } else {
            StateListDrawable()
        }


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBg()
        setTxtColor()
    }

    private fun setTxtColor() {
        textColorStateList =
            ColorStateList(arrayOf(STATE_NORMAL, STATE_SELECT), intArrayOf(txtColor, txtColorSel))
        setTextColor(textColorStateList)
    }


    private fun setBg() {

        val bgNormal = GradientDrawable()
        val bgSelect = GradientDrawable()

        if (autoRound) {
            bgNormal.cornerRadius = max(measuredWidth, measuredHeight).toFloat() / 2F
            bgSelect.cornerRadius = max(measuredWidth, measuredHeight).toFloat() / 2F
        } else {
            bgNormal.cornerRadius = roundRadius
            bgSelect.cornerRadius = roundRadius
        }

        if (gradient) {
            bgNormal.colors = intArrayOf(bgColorFrom, bgColorTo)
            bgNormal.orientation = getOrientation(gradientOrientation)
            bgSelect.colors = intArrayOf(bgColorFromSel, bgColorToSel)
            bgSelect.orientation = getOrientation(gradientOrientation)
        } else {
            bgNormal.setColor(bgColor)
            bgSelect.setColor(bgColorSel)
        }

        if (strokeWidth > 0) {
            bgNormal.setStroke(strokeWidth.toInt(), strokeColor)
        }

        bgStateListDrawable.addState(STATE_NORMAL, bgNormal)
        bgStateListDrawable.addState(STATE_SELECT, bgSelect)

        background = bgStateListDrawable
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
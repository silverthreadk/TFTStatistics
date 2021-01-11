package com.silverthread.tftstatistics.ui.matchHistory

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.silverthread.tftstatistics.R

class StarView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val DEFAULT_COLOR = Color.BLACK
    }

    var color = DEFAULT_COLOR
        set(value){
            field = value
            invalidate()
        }

    private val paint = Paint()
    private val path = Path()
    private var size = 0

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawStar(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    private fun drawStar(canvas: Canvas) {
        val mid = width / 2.toFloat()
        val min = minOf(width, height).toFloat()
        val fat = min / 17
        val offset = mid - min

        paint.color = color
        paint.style = Paint.Style.FILL

        path.reset()

        path.moveTo(offset + min * 0.5f, offset + min * 0.84f)
        path.lineTo(offset + min * 1.5f, offset + min * 0.84f)
        path.lineTo(offset + min * 0.68f, offset + min * 1.45f)
        path.lineTo(offset + min * 1.0f, offset + min * 0.5f)
        path.lineTo(offset + min * 1.32f, offset + min * 1.45f)
        path.lineTo(offset + min * 0.5f, offset + min * 0.84f)

        path.close()
        canvas.drawPath(path, paint)
    }


    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.StarView,
                0, 0)
        color = typedArray.getColor(R.styleable.StarView_color, DEFAULT_COLOR)
        typedArray.recycle()
    }
}
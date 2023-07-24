package com.parniyan.logoprogressbar


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.toRect

class ProgressbarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var progress: Float = 0f
    private var progressColor: Int = Color.BLUE
    private var progressColors: IntArray = intArrayOf()
    private var progressThickness: Float = 10f
    private var markerDrawable: Drawable? = null
    private var markerSize: Float = 0f
    private var textColor: Int = Color.BLACK
    private var textSize: Float = 14f
    private var borderSize: Float = 0f
    private var borderColor: Int = Color.TRANSPARENT
    private var useRainBowColor: Boolean = false

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomProgressBar, 0, 0
        )
        try {
            progressColor = typedArray.getColor(
                R.styleable.CustomProgressBar_progressColor,
                Color.BLUE
            )
            progressThickness = typedArray.getDimension(
                R.styleable.CustomProgressBar_progressThickness,
                10f
            )
            markerDrawable = typedArray.getDrawable(R.styleable.CustomProgressBar_markerDrawable)
            textColor = typedArray.getColor(
                R.styleable.CustomProgressBar_textColor,
                Color.BLACK
            )
            textSize = typedArray.getDimension(
                R.styleable.CustomProgressBar_textSize,
                14f
            )
            progress = typedArray.getFloat(R.styleable.CustomProgressBar_progress, 0f)
            if (typedArray.hasValue(R.styleable.CustomProgressBar_progressColors)) {
                progressColors = typedArray.getResourceId(
                    R.styleable.CustomProgressBar_progressColors,
                    0
                ).let { resourceId ->
                    context.resources.getIntArray(resourceId)
                }
            }
            markerSize = typedArray.getDimension(
                R.styleable.CustomProgressBar_markerSize,
                0f
            )
        } finally {
            typedArray.recycle()
        }

        progressPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        borderPaint.style = Paint.Style.STROKE
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Calculate the progress bar dimensions
        val width = width.toFloat()
        val height = height.toFloat()
        val progressWidth = progress / 100 * width
        val progressHeight = progressThickness.coerceAtLeast(1f)



            // Draw the progress bar
            if (progressColors.isNotEmpty()) {
                val colorPositions = FloatArray(progressColors.size)
                val colorStep = 1f / (progressColors.size - 1)
                for (i in progressColors.indices) {
                    colorPositions[i] = i * colorStep
                }
                val progressGradient = LinearGradient(
                    0f,
                    height / 2,
                    width,
                    height / 2,
                    progressColors,
                    colorPositions,
                    Shader.TileMode.CLAMP
                )
                progressPaint.shader = progressGradient
            } else {
                progressPaint.color = progressColor
            }



        // Draw the border around the progress bar
        if (borderSize > 0) {
            borderPaint.color = borderColor
            borderPaint.strokeWidth = borderSize
            val halfBorderSize = borderSize / 2
            val borderRect = RectF(
                halfBorderSize,
                height / 2 - progressHeight / 2 - halfBorderSize,
                width - halfBorderSize,
                height / 2 + progressHeight / 2 + halfBorderSize
            )
            canvas?.drawRect(borderRect, borderPaint)
        }

        // Draw the progress bar
        canvas?.drawRect(
            0f,
            height / 2 - progressHeight / 2,
            progressWidth,
            height / 2 + progressHeight / 2,
            progressPaint
        )

        // Draw the marker
        markerDrawable?.let {
            val markerHeight = markerSize.coerceAtLeast(height)
            val aspectRatio = it.intrinsicWidth.toFloat() / it.intrinsicHeight.toFloat()
            val maxHeight = markerHeight
            val maxWidth = maxHeight * aspectRatio
            val markerLeft = progressWidth - maxWidth / 2
            val markerTop = height / 2 - maxHeight / 2
            val markerRect = RectF(
                markerLeft,
                markerTop,
                markerLeft + maxWidth,
                markerTop + maxHeight
            )
            it.bounds = markerRect.toRect()
            if (canvas != null) {
                it.draw(canvas)
            }
        }

        // Draw the progress percentage text
        textPaint.color = textColor
        textPaint.textSize = textSize
        val text = "${progress.toInt()}%"
        canvas?.drawText(
            text,
            width / 2,
            height / 2 + textPaint.textSize / 2,
            textPaint
        )
    }


    fun setProgressColor(color: Int) {
        progressColor = color
        progressColors = intArrayOf()
        invalidate()
    }

    fun setProgressColorPalette(colors: IntArray) {
        progressColors = colors
        progressColor = Color.BLUE
        invalidate()
    }

    fun setProgressBarThickness(thickness: Float) {
        progressThickness = thickness
        invalidate()
    }

    fun setMarkerDrawable(drawable: Drawable?) {
        markerDrawable = drawable
        invalidate()
    }

    fun setMarkerSize(size: Float) {
        markerSize = size
        invalidate()
    }

    fun setTextColor(color: Int) {
        textColor = color
        invalidate()
    }

    fun setTextSize(size: Float) {
        textSize = size
        invalidate()
    }

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, 100f)
        invalidate()
    }

    fun setBorderSize(size: Float) {
        borderSize = size
        invalidate()
    }

    fun setBorderColor(color: Int) {
        borderColor = color
        invalidate()
    }
}

package com.demo

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.core.content.ContextCompat
import android.view.View

class DemoDecoration(context: Context, left: Float, top: Float, right: Float, bottom: Float) : ItemDecoration() {

    companion object {
        private const val dividerHeight = 8f
        private const val dividerPaddingLeft = 0f
    }

    private val left: Int
    private val top: Int
    private val right: Int
    private val bottom: Int
    private val dividerPaddingRight = 0f
    private val dividerPaint: Paint
    private val textPaint: Paint

    init {
        val density = context.resources.displayMetrics.density
        this.left = (density * left).toInt()
        this.top = (density * top).toInt()
        this.right = (density * right).toInt()
        this.bottom = (density * bottom).toInt()
        dividerPaint = Paint()
        dividerPaint.isAntiAlias = true
        dividerPaint.style = Paint.Style.FILL
        dividerPaint.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 30f
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        draw(canvas, parent)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        draw(canvas, parent)
    }

    private fun draw(canvas: Canvas, parent: RecyclerView) {
        if (parent.layoutManager == null) {
            return
        }
        canvas.save()
        drawRect(canvas, parent)
        canvas.restore()
    }

    private fun drawRect(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val spanCount = getSpanCount(parent)
        val space = 0f.coerceAtLeast(((top + bottom).toFloat() - dividerHeight) / 2)
        var i = 0
        if (i < childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            if (position / spanCount % 2 == 0) {
                canvas.drawRect(dividerPaddingLeft, view.bottom + space,
                    parent.right - dividerPaddingRight, view.bottom + space + dividerHeight,
                    dividerPaint)
            }
            i += spanCount
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) {
            return
        }
        val position = parent.getChildAdapterPosition(view)
        val spanCount = getSpanCount(parent)
        val index = position / spanCount
        if (index > 0 && index % 2 == 0) {
            outRect[left, 56, right] = bottom
        } else {
            outRect[left, top, right] = bottom
        }
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

}
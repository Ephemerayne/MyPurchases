package com.nyx.mypurchases.common.swipehandler

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nyx.mypurchases.R
import com.nyx.mypurchases.utils.dpToFloat

abstract class SwipeToDeleteCallback(
    context: Context,
    private val cornerRadiusDp: Int? = null,
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth
    private val intrinsicHeight = deleteIcon?.intrinsicHeight
    private val background = GradientDrawable()
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        if (viewHolder.adapterPosition == 10) return 0
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        intrinsicHeight ?: return
        intrinsicWidth ?: return
        deleteIcon ?: return

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        background.color = ColorStateList.valueOf(
            ContextCompat.getColor(recyclerView.context, R.color.delete_item_background)
        )
        background.setStroke(
            2,
            ColorStateList.valueOf(
                ContextCompat.getColor(recyclerView.context, android.R.color.transparent)
            )
        )

        if (cornerRadiusDp != null) setCorners(recyclerView, cornerRadiusDp)

        background.setBounds(
            itemView.right + dX.toInt() - 40,
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

    private fun setCorners(recyclerView: RecyclerView, cornerRadiusDp: Int) {
        background.cornerRadius = dpToFloat(recyclerView.context, cornerRadiusDp)
    }
}
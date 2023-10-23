package com.nyx.mypurchases.common.swipehandler

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

open class SwipeHandler(val context: Context) {

    fun <T : RecyclerView.ViewHolder?> init(
        recyclerView: RecyclerView,
        cornerRadiusDp: Int? = null,
        onSwiped: (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit,
    ) {
        val swipeHandler = object : SwipeToDeleteCallback(context, cornerRadiusDp) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwiped(viewHolder, direction)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
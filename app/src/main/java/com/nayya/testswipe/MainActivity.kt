package com.nayya.testswipe

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.lang.Math.max

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemAdapter(getItems())
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Nothing to do here
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemViewHolder = viewHolder as ItemAdapter.ItemViewHolder

                    val width = itemViewHolder.menuLayout.width.toFloat()
                    if (dX < 0) { // Swiping left
                        // Calculate translation so that menuLayout appears to follow the contentLayout
                        val translationX = max(dX, -width)
                        itemViewHolder.contentLayout.translationX = translationX
                        itemViewHolder.menuLayout.translationX = translationX + width
                        if (itemViewHolder.menuLayout.visibility != View.VISIBLE) {
                            itemViewHolder.menuLayout.visibility = View.VISIBLE
                        }
                    } else { // Swiping right
                        itemViewHolder.contentLayout.translationX = 0f
                        itemViewHolder.menuLayout.translationX = itemViewHolder.contentLayout.width.toFloat()
                        itemViewHolder.menuLayout.visibility = View.GONE
                    }
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                val itemViewHolder = viewHolder as ItemAdapter.ItemViewHolder
                if (itemViewHolder.contentLayout.translationX < 0) {
                    itemViewHolder.contentLayout.translationX = 0f
                    itemViewHolder.menuLayout.visibility = View.GONE
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getItems(): MutableList<Item> {
        return mutableListOf(
            Item("Item 1", "Description 1"),
            Item("Item 2", "Description 2"),
            Item("Item 3", "Description 3"),
            Item("Item 4", "Description 4"),
            Item("Item 5", "Description 5"),
            Item("Item 6", "Description 6"),
            Item("Item 7", "Description 7"),
            Item("Item 8", "Description 8"),
            Item("Item 9", "Description 9"),
            Item("Item 10", "Description 10")
        )
    }
}
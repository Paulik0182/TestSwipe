package com.nayya.testswipe

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
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
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // No action required on swiped
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemViewHolder = viewHolder as ItemAdapter.ItemViewHolder
                    val width = itemViewHolder.menuLayout.width.toFloat()

                    // Ограничиваем сдвиг contentLayout, чтобы menuLayout оставался видимым, но не двигался
                    val translationX = dX.coerceAtLeast(-width)
                    itemViewHolder.contentLayout.translationX = translationX

                    // Фиксируем menuLayout, чтобы он не сдвигался
                    itemViewHolder.menuLayout.translationX = 0f  // Это гарантирует, что menuLayout не уйдёт за пределы экрана

                    // Управление видимостью menuLayout
                    if (translationX == -width) {
                        if (itemViewHolder.menuLayout.visibility != View.VISIBLE) {
                            itemViewHolder.menuLayout.visibility = View.VISIBLE
                            // Привязываем обработчики нажатий здесь
                            itemViewHolder.deleteButton.setOnClickListener {
                                Log.d("@@@", "onChildDraw() called")

                                itemViewHolder.removeItem(itemViewHolder.adapterPosition)
                                resetSwipe(itemViewHolder)
                            }
                            itemViewHolder.editButton.setOnClickListener {
                                Log.d("@@@", "onChildDraw() called")
//                                Toast.makeText(itemView.context, "Edited item: ${itemViewHolder.currentItem?.title}", Toast.LENGTH_SHORT).show()
                                resetSwipe(itemViewHolder)
                            }
                        }
                    } else {
                        itemViewHolder.menuLayout.visibility = View.GONE
                    }
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }

            private fun resetSwipe(itemViewHolder: ItemAdapter.ItemViewHolder) {
                itemViewHolder.contentLayout.translationX = 0f
                itemViewHolder.menuLayout.visibility = View.GONE
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                val itemViewHolder = viewHolder as ItemAdapter.ItemViewHolder
                itemViewHolder.contentLayout.translationX = 0f
                itemViewHolder.menuLayout.visibility = View.GONE
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
        adapter.setItemTouchHelper(itemTouchHelper)
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
package com.nayya.testswipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemAdapter(getItems())
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.handleSwipe(viewHolder.adapterPosition, direction)
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
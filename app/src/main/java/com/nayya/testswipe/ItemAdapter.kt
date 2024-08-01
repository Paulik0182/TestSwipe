package com.nayya.testswipe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class ItemAdapter(private val items: MutableList<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentLayout: View = itemView.findViewById(R.id.contentLayout)
        val menuLayout: View = itemView.findViewById(R.id.menuLayout)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val editButton: Button = itemView.findViewById(R.id.editButton)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        private var currentItem: Item? = null
        private var currentPosition: Int = -1

        init {
            deleteButton.setOnClickListener {
                Log.d("@@@", "null() called  Del")
                Toast.makeText(itemView.context, "Deleted item: ${currentItem?.title}", Toast.LENGTH_SHORT).show()
                removeItem(currentPosition)
            }

            editButton.setOnClickListener {
                Log.d("@@@", "null() called Edit")
                Toast.makeText(itemView.context, "Edited item: ${currentItem?.title}", Toast.LENGTH_SHORT).show()
                // Implement edit functionality here
            }
        }

        fun bind(item: Item) {
            currentItem = item
            currentPosition = adapterPosition
            titleTextView.text = item.title
            descriptionTextView.text = item.description
            contentLayout.translationX = 0f // Возвращаем содержимое на место
            menuLayout.visibility = View.GONE // Скрываем меню по умолчанию
        }

        private fun removeItem(position: Int) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}
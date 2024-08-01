package com.nayya.testswipe

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
        private val contentLayout: View = itemView.findViewById(R.id.contentLayout)
        private val menuLayout: View = itemView.findViewById(R.id.menuLayout)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        private val editButton: Button = itemView.findViewById(R.id.editButton)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        private var currentItem: Item? = null
        private var currentPosition: Int = -1
        private var deltaX = 0f // Текущее смещение элемента по оси X

        init {
            deleteButton.setOnClickListener {
                Toast.makeText(itemView.context, "Deleted item: ${currentItem?.title}", Toast.LENGTH_SHORT).show()
                removeItem(currentPosition)
            }

            editButton.setOnClickListener {
                Toast.makeText(itemView.context, "Edited item: ${currentItem?.title}", Toast.LENGTH_SHORT).show()
                // Implement edit functionality here
            }
        }

        fun bind(item: Item) {
            currentItem = item
            currentPosition = adapterPosition
            titleTextView.text = item.title
            descriptionTextView.text = item.description
            menuLayout.translationX = -menuLayout.width.toFloat() // Изначально скрываем меню
            deltaX = 0f // Сбрасываем текущее смещение элемента
            updateLayout()
        }

        fun handleSwipe(direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    deltaX = max(deltaX - 20f, -menuLayout.width.toFloat()) // Плавно смещаем элемент влево
                    updateLayout()
                }
                ItemTouchHelper.RIGHT -> {
                    deltaX = min(deltaX + 20f, 0f) // Плавно смещаем элемент вправо
                    updateLayout()
                }
            }
        }

        private fun updateLayout() {
            contentLayout.translationX = deltaX // Применяем текущее смещение элемента
            menuLayout.translationX = deltaX - menuLayout.width // Синхронизируем положение меню
            deleteButton.visibility = if (deltaX <= -menuLayout.width.toFloat()) View.VISIBLE else View.INVISIBLE
            editButton.visibility = if (deltaX <= -menuLayout.width.toFloat()) View.VISIBLE else View.INVISIBLE
        }

        private fun removeItem(position: Int) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun handleSwipe(position: Int, direction: Int) {
        items[position].let { item ->
            val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position) as? ItemViewHolder
            viewHolder?.handleSwipe(direction)
        }
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}
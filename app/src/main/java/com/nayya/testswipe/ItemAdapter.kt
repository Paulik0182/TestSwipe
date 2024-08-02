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

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view, itemTouchHelper)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    inner class ItemViewHolder(itemView: View, private val itemTouchHelper: ItemTouchHelper) : RecyclerView.ViewHolder(itemView) {
        val contentLayout: ViewGroup = itemView.findViewById(R.id.contentLayout)
        val menuLayout: ViewGroup = itemView.findViewById(R.id.menuLayout)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)



        fun bind(item: Item) {
            titleTextView.text = item.title
            descriptionTextView.text = item.description


            contentLayout.translationX = 0f
            menuLayout.visibility = View.GONE
        }

        fun removeItem(position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}
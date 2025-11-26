package com.example.grocerytrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerytrack.databinding.ItemGroceryBinding

/**
 * Simple adapter that displays grocery items without removing them on tap.
 */
class ReadOnlyGroceryAdapter : RecyclerView.Adapter<ReadOnlyGroceryAdapter.ViewHolder>() {

    private val items = mutableListOf<GroceryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(newItems: List<GroceryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemGroceryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroceryItem) {
            binding.groceryNameTextView.text = item.name
        }
    }
}

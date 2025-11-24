package com.example.grocerytrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerytrack.databinding.ItemGroceryBinding

/**
 * RecyclerView adapter that renders a simple text-only grocery list.
 * The list is backed by a MutableList so we can easily add/remove items.
 */
class GroceryAdapter(
    private val items: MutableList<GroceryItem>,
    private val onItemClicked: (GroceryItem) -> Unit
) : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding = ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /**
     * Add an item at the top of the list and notify the adapter.
     */
    fun addItem(item: GroceryItem) {
        items.add(0, item)
        notifyItemInserted(0)
    }

    /**
     * Remove the item at the given position if it exists.
     */
    private fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class GroceryViewHolder(private val binding: ItemGroceryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Remove the item when it is tapped and inform the caller.
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(items[position])
                    removeItem(position)
                }
            }
        }

        fun bind(item: GroceryItem) {
            binding.groceryNameTextView.text = item.name
        }
    }
}

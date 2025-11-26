package com.example.grocerytrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerytrack.databinding.ItemSavedListBinding

/**
 * Renders saved grocery lists and notifies when one is tapped.
 */
class SavedListAdapter(
    private val onListClicked: (GroceryList) -> Unit
) : RecyclerView.Adapter<SavedListAdapter.SavedListViewHolder>() {

    private val lists = mutableListOf<GroceryList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedListViewHolder {
        val binding = ItemSavedListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedListViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount(): Int = lists.size

    fun submitLists(newLists: List<GroceryList>) {
        lists.clear()
        lists.addAll(newLists)
        notifyDataSetChanged()
    }

    inner class SavedListViewHolder(private val binding: ItemSavedListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onListClicked(lists[position])
                }
            }
        }

        fun bind(list: GroceryList) {
            binding.listName.text = list.name
            binding.listCount.text = binding.root.context.getString(
                R.string.item_count_format,
                list.items.size
            )
        }
    }
}

package com.example.grocerytrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerytrack.databinding.ItemPriceBinding
import java.text.NumberFormat
import java.util.Locale

/**
 * RecyclerView adapter to display price-per-unit entries.
 * Items can be tapped to remove them from the list.
 */
class PriceAdapter(
    private val items: MutableList<PriceItem>,
    private val onItemRemoved: (PriceItem) -> Unit
) : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val binding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /**
     * Add a new price entry to the top of the list.
     */
    fun addItem(item: PriceItem) {
        items.add(0, item)
        notifyItemInserted(0)
    }

    private fun removeItem(position: Int) {
        if (position in items.indices) {
            val removed = items.removeAt(position)
            notifyItemRemoved(position)
            onItemRemoved(removed)
        }
    }

    inner class PriceViewHolder(private val binding: ItemPriceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position)
                }
            }
        }

        fun bind(item: PriceItem) {
            binding.priceNameTextView.text = item.name
            binding.priceValueTextView.text = currencyFormatter.format(item.pricePerUnit)
        }
    }
}

package com.example.grocerytrack

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerytrack.databinding.ActivityPriceBinding

/**
 * Screen for recording price-per-unit for grocery items.
 */
class PriceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPriceBinding
    private lateinit var adapter: PriceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupInput()
    }

    private fun setupRecyclerView() {
        adapter = PriceAdapter(mutableListOf()) { removedItem ->
            Toast.makeText(this, "Removed: ${removedItem.name}", Toast.LENGTH_SHORT).show()
        }
        binding.priceRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.priceRecyclerView.adapter = adapter
    }

    private fun setupInput() {
        binding.addPriceButton.setOnClickListener {
            addPriceItem()
        }

        binding.pricePerUnitEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addPriceItem()
                true
            } else {
                false
            }
        }
    }

    private fun addPriceItem() {
        val name = binding.priceItemNameEditText.text?.toString()?.trim().orEmpty()
        val price = binding.pricePerUnitEditText.text?.toString()?.trim()?.toDoubleOrNull()

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show()
            return
        }

        if (price == null || price < 0) {
            Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            return
        }

        adapter.addItem(PriceItem(name, price))
        binding.priceItemNameEditText.text?.clear()
        binding.pricePerUnitEditText.text?.clear()
        binding.priceRecyclerView.scrollToPosition(0)
    }
}

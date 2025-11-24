package com.example.grocerytrack

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerytrack.databinding.ActivityMainBinding

/**
 * The single activity for the app that shows the grocery list.
 * It wires up the RecyclerView, input field, and button using ViewBinding.
 */
class MainActivity : AppCompatActivity() {

    // Binding object generated from activity_main.xml for easy view access
    private lateinit var binding: ActivityMainBinding

    // Adapter that backs the RecyclerView with a mutable list of GroceryItem
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupInput()
        setupPriceNavigation()
    }

    /**
     * Prepare the RecyclerView with a vertical layout and the adapter.
     * The adapter callback shows a toast when an item is removed.
     */
    private fun setupRecyclerView() {
        adapter = GroceryAdapter(mutableListOf()) { removedItem ->
            Toast.makeText(this, "Removed: ${removedItem.name}", Toast.LENGTH_SHORT).show()
        }
        binding.groceryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.groceryRecyclerView.adapter = adapter
    }

    /**
     * Hook up the Add button and keyboard action so both add new items.
     */
    private fun setupInput() {
        binding.addButton.setOnClickListener {
            addItem()
        }

        binding.itemEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addItem()
                true
            } else {
                false
            }
        }
    }

    /**
     * Read the text, validate it, add it to the list, and clear the input.
     */
    private fun addItem() {
        val name = binding.itemEditText.text?.toString()?.trim().orEmpty()
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show()
            return
        }
        adapter.addItem(GroceryItem(name))
        binding.itemEditText.text?.clear()
        binding.groceryRecyclerView.scrollToPosition(0)
    }

    /**
     * Navigate to the price-per-unit screen to log costs for groceries.
     */
    private fun setupPriceNavigation() {
        binding.recordPriceButton.setOnClickListener {
            startActivity(Intent(this, PriceActivity::class.java))
        }
    }
}

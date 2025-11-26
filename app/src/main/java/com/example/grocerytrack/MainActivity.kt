package com.example.grocerytrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.startListButton.setOnClickListener {
            startActivity(Intent(this, StartShoppingActivity::class.java))
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

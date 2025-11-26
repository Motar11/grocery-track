package com.example.grocerytrack

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerytrack.databinding.ActivityGroceryListBinding

/**
 * Lets the user build a grocery list, then save it for later reuse.
 */
class GroceryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroceryListBinding
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupInput()
        setupSave()
    }

    private fun setupRecyclerView() {
        adapter = GroceryAdapter(mutableListOf()) { removedItem ->
            Toast.makeText(this, "Removed: ${removedItem.name}", Toast.LENGTH_SHORT).show()
        }
        binding.groceryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.groceryRecyclerView.adapter = adapter
    }

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

    private fun setupSave() {
        binding.saveButton.setOnClickListener {
            val listName = binding.listNameEditText.text?.toString()?.trim().orEmpty()
            if (listName.isEmpty()) {
                Toast.makeText(this, "Please name your list", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (adapter.isEmpty()) {
                Toast.makeText(this, "Add at least one item", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            SavedListsRepository.saveList(
                context = this,
                groceryList = GroceryList(
                    name = listName,
                    items = adapter.getItems()
                )
            )

            Toast.makeText(this, "List saved", Toast.LENGTH_SHORT).show()
            binding.listNameEditText.text?.clear()
            binding.itemEditText.text?.clear()
            adapter.clear()
        }
    }

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
}

package com.example.grocerytrack

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerytrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applySystemInsets()

        setupRecyclerView()
        setupInput()
    }

    private fun setupRecyclerView() {
        adapter = GroceryAdapter(mutableListOf()) { removedItem ->
            Toast.makeText(this, "Removed: ${removedItem.name}", Toast.LENGTH_SHORT).show()
        }
        binding.groceryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.groceryRecyclerView.adapter = adapter
    }

    private fun applySystemInsets() {
        val initialPadding = binding.root.run {
            listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                initialPadding[0] + insets.left,
                initialPadding[1] + insets.top,
                initialPadding[2] + insets.right,
                initialPadding[3] + insets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
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

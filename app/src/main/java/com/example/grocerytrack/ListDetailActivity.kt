package com.example.grocerytrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerytrack.databinding.ActivityListDetailBinding

/**
 * Shows the items for a saved grocery list so the user can start shopping.
 */
class ListDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIST_NAME = "extra_list_name"
    }

    private lateinit var binding: ActivityListDetailBinding
    private lateinit var adapter: ReadOnlyGroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ReadOnlyGroceryAdapter()
        binding.itemsRecycler.layoutManager = LinearLayoutManager(this)
        binding.itemsRecycler.adapter = adapter

        val listName = intent.getStringExtra(EXTRA_LIST_NAME)
        if (listName != null) {
            binding.listTitle.text = listName
            val list = SavedListsRepository.loadLists(this).firstOrNull { it.name == listName }
            adapter.submitItems(list?.items.orEmpty())
        } else {
            binding.listTitle.text = getString(R.string.list_not_found)
        }
    }
}

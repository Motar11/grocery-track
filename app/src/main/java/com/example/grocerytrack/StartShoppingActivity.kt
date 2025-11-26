package com.example.grocerytrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerytrack.databinding.ActivityStartShoppingBinding

/**
 * Displays the saved grocery lists so the user can pick one to start shopping with.
 */
class StartShoppingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartShoppingBinding
    private lateinit var adapter: SavedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SavedListAdapter { selectedList ->
            val intent = Intent(this, ListDetailActivity::class.java)
            intent.putExtra(ListDetailActivity.EXTRA_LIST_NAME, selectedList.name)
            startActivity(intent)
        }

        binding.savedListsRecycler.layoutManager = LinearLayoutManager(this)
        binding.savedListsRecycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.submitLists(SavedListsRepository.loadLists(this))
        binding.emptyStateText.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
    }
}

package com.example.grocerytrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grocerytrack.databinding.ActivityMainBinding

/**
 * Landing screen that presents navigation to the three core flows: creating a list,
 * starting a saved list, and recording price-per-unit information.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    /**
     * Wire up the menu buttons to launch their respective screens.
     */
    private fun setupNavigation() {
        binding.createListButton.setOnClickListener {
            startActivity(Intent(this, GroceryListActivity::class.java))
        }

        binding.startListButton.setOnClickListener {
            startActivity(Intent(this, StartShoppingActivity::class.java))
        }

        binding.priceButton.setOnClickListener {
            startActivity(Intent(this, PriceActivity::class.java))
        }
    }
}

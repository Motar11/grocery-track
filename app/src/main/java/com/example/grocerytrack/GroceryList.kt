package com.example.grocerytrack

/**
 * Represents a saved grocery list with a user-friendly name.
 */
data class GroceryList(
    val name: String,
    val items: List<GroceryItem>
)

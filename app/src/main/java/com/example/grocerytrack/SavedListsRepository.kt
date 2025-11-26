package com.example.grocerytrack

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * Very small persistence helper that stores grocery lists in SharedPreferences as JSON.
 */
object SavedListsRepository {

    private const val PREFS_NAME = "grocery_prefs"
    private const val KEY_SAVED_LISTS = "saved_lists"

    fun saveList(context: Context, groceryList: GroceryList) {
        val current = loadLists(context).toMutableList()
        current.removeAll { it.name.equals(groceryList.name, ignoreCase = true) }
        current.add(0, groceryList)
        persist(context, current)
    }

    fun loadLists(context: Context): List<GroceryList> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_SAVED_LISTS, null) ?: return emptyList()
        val array = JSONArray(json)
        return buildList {
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val name = obj.getString("name")
                val itemsArray = obj.getJSONArray("items")
                val items = mutableListOf<GroceryItem>()
                for (j in 0 until itemsArray.length()) {
                    val itemName = itemsArray.getString(j)
                    items.add(GroceryItem(itemName))
                }
                add(GroceryList(name, items))
            }
        }
    }

    private fun persist(context: Context, lists: List<GroceryList>) {
        val array = JSONArray()
        lists.forEach { list ->
            val obj = JSONObject()
            obj.put("name", list.name)
            val itemsArray = JSONArray()
            list.items.forEach { item -> itemsArray.put(item.name) }
            obj.put("items", itemsArray)
            array.put(obj)
        }
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_SAVED_LISTS, array.toString())
            .apply()
    }
}

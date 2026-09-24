package com.thestackdigest.app.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class ArticleConverters {

    @TypeConverter
    fun fromCategories(categories: List<String>): String {
        return JSONArray(categories).toString()
    }

    @TypeConverter
    fun toCategories(value: String): List<String> {

        val jsonArray = JSONArray(value)

        return List(jsonArray.length()) { index ->
            jsonArray.getString(index)
        }
    }
}
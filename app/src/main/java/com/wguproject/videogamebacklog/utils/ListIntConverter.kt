package com.wguproject.videogamebacklog.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListIntConverter {
    @TypeConverter
    fun fromString(value: String ): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Int>): String {
        return Gson().toJson(list)
    }
}
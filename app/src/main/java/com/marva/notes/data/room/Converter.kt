package com.marva.notes.data.room

import androidx.room.TypeConverter
import com.marva.notes.data.entity.Priority

class Converter {

    //untuk convert dari priority enum class ke string
    //fungsi ini dipanggil ketika get sebuah database
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    //ini untuk konvert sebuah string kedalam enum class priority
    //fungsi ini dipanggil ketika add dan update sebuah data kedalam database
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
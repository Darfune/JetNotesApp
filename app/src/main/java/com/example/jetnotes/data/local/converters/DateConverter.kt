package com.example.jetnotes.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(date: Long?): Date? = date?.let { Date(it) }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}
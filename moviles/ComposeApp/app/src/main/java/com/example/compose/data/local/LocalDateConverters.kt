package com.example.compose.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * converts List to and from String
 */
class LocalDateConverters {

    @TypeConverter
    fun localDateToString(value: LocalDate): String =
        value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))



    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

}
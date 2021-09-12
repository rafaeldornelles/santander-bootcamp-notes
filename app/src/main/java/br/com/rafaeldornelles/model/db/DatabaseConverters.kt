package br.com.rafaeldornelles.model.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class DatabaseConverters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate) = value.toEpochDay()

    @TypeConverter
    fun toLocalDate(value: Long): LocalDate = LocalDate.ofEpochDay(value)

    @TypeConverter
    fun fromLocalTime(value: LocalTime) = value.toSecondOfDay().toLong()

    @TypeConverter
    fun toLocalTime(value: Long): LocalTime = LocalTime.ofSecondOfDay(value)
}
package me.asayah.reservatium.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTimeConverters private constructor() {
    companion object {

        @JvmStatic
        @TypeConverter
        fun toLocalDate(date: String?): LocalDate? {
            return if (!date.isNullOrEmpty())
                LocalDate.parse(date)
            else null
        }

        @JvmStatic
        @TypeConverter
        fun fromLocalDate(date: LocalDate?): String? {
            return if (date != null)
                DateTimeFormatter.ISO_LOCAL_DATE.format(date)
            else null
        }
    }
}
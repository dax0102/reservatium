package me.asayah.reservatium.database.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeConverters private constructor() {
    companion object {

        @JvmStatic
        @TypeConverter
        fun toLocalDateTime(dateTime: String?): LocalDateTime? {
            return if (!dateTime.isNullOrEmpty())
                LocalDateTime.parse(dateTime)
            else null
        }

        @JvmStatic
        @TypeConverter
        fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
            return if (dateTime != null)
                DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime)
            else null
        }
    }
}
package me.asayah.reservatium.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.asayah.reservatium.database.converters.DateTimeConverters
import me.asayah.reservatium.database.dao.CustomerDao
import me.asayah.reservatium.database.dao.ReservationDao
import me.asayah.reservatium.database.dao.RoomDao
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.reservation.ReservationCore
import me.asayah.reservatium.features.room.Room

@androidx.room.Database(entities = [Room::class, Customer::class, ReservationCore::class],
    version = Database.DATABASE_VERSION, exportSchema = true)
@TypeConverters(DateTimeConverters::class)
abstract class Database: RoomDatabase() {

    abstract fun reservations(): ReservationDao
    abstract fun rooms(): RoomDao
    abstract fun customers(): CustomerDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "reservium"

        private var instance: Database? = null

        fun getInstance(context: Context): Database {
            if (instance == null) {
                synchronized(Database::class) {
                    instance = androidx.room.Room.databaseBuilder(context.applicationContext,
                        Database::class.java, DATABASE_NAME)
                        .build()
                }
            }
            return instance!!
        }
    }
}
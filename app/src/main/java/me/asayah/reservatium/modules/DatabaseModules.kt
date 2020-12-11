package me.asayah.reservatium.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import me.asayah.reservatium.database.Database
import me.asayah.reservatium.database.dao.CustomerDao
import me.asayah.reservatium.database.dao.ReservationDao
import me.asayah.reservatium.database.dao.RoomDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModules {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Database.getInstance(context)
    }

    @Provides
    fun provideRoomDao(database: Database): RoomDao = database.rooms()
    @Provides
    fun provideCustomerDao(database: Database): CustomerDao = database.customers()
    @Provides
    fun provideReservation(database: Database): ReservationDao = database.reservations()

}
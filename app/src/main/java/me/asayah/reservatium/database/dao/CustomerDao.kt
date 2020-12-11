package me.asayah.reservatium.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.asayah.reservatium.features.customer.Customer

@Dao
interface CustomerDao {

    @Insert
    suspend fun insert(customer: Customer)

    @Delete
    suspend fun remove(customer: Customer)

    @Update
    suspend fun update(customer: Customer)

    @Transaction
    @Query("SELECT * FROM customers")
    fun fetch(): LiveData<List<Customer>>

}
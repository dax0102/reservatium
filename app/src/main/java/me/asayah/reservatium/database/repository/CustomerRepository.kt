package me.asayah.reservatium.database.repository

import androidx.lifecycle.LiveData
import me.asayah.reservatium.database.dao.CustomerDao
import me.asayah.reservatium.features.customer.Customer
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val customerDao: CustomerDao) {

    suspend fun insert(customer: Customer) = customerDao.insert(customer)

    suspend fun remove(customer: Customer) = customerDao.remove(customer)

    suspend fun update(customer: Customer) = customerDao.update(customer)

    suspend fun fetch(): LiveData<List<Customer>> = customerDao.fetch()

}
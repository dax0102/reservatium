package me.asayah.reservatium.features.customer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.CustomerRepository

class CustomerViewModel @ViewModelInject constructor(
        private val customerRepository: CustomerRepository
): ViewModel() {

    val customers: LiveData<List<Customer>> = customerRepository.fetch()
    val isEmpty: LiveData<Boolean> = Transformations.map(customers) { it.isEmpty() }

    fun insert(customer: Customer) = viewModelScope.launch { customerRepository.insert(customer) }

    fun remove(customer: Customer) = viewModelScope.launch { customerRepository.remove(customer) }

    fun update(customer: Customer) = viewModelScope.launch { customerRepository.update(customer) }

}
package me.asayah.reservatium.features.customer.editor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.ReservationRepository
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.reservation.ReservationBundle

class CustomerEditorViewModel @ViewModelInject constructor(
        private val reservationRepository: ReservationRepository
): ViewModel() {

    var customer = Customer()
        set(value) {
            field = value
            _customer.value = value
            fetchWithCustomerId(value.customerId)
        }

    private val _reservations: MutableLiveData<List<ReservationBundle>> = MutableLiveData(emptyList())
    internal val reservations: LiveData<List<ReservationBundle>> = _reservations
    internal val isEmpty: LiveData<Boolean> = Transformations.map(reservations) { it.isEmpty() }

    private val _customer: MutableLiveData<Customer> = MutableLiveData()
    internal val customerLive: LiveData<Customer> = _customer

    private fun fetchWithCustomerId(id: String?) = viewModelScope.launch {
        _reservations.value = reservationRepository.fetchWithCustomer(id)
    }
}
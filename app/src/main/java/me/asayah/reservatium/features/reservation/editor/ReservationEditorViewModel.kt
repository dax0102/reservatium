package me.asayah.reservatium.features.reservation.editor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.ReservationRepository
import me.asayah.reservatium.features.core.DateRange
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.room.Room

class ReservationEditorViewModel @ViewModelInject constructor(
    private val reservationRepository: ReservationRepository
): ViewModel() {

    var room: Room? = null
        set(value) {
            field = value
            _roomLive.value = value
            checkReservation()
        }
    var customer: Customer? = null
        set(value) {
            field = value
            _customerLive.value = value
            checkReservation()
        }
    var reservation = Reservation()
        get() {
            field.room = room?.roomId
            field.customer = customer?.customerId
            field.startDate = date?.startDate
            field.endDate = date?.endDate
            field.numberOfGuests = numberOfGuests
            return field
        }
    var date: DateRange? = null
        set(value) {
            field = value
            _dateLive.value = value
            checkReservation()
        }
    var numberOfGuests: Int = 1

    private var _status = MutableLiveData(ReservationStatus.CONFLICT_NONE)
    internal val status: LiveData<ReservationStatus> = _status

    private val _roomLive = MutableLiveData(room)
    internal val roomLive: LiveData<Room?> = _roomLive

    private val _customerLive = MutableLiveData(customer)
    internal val customerLive: LiveData<Customer?> = _customerLive

    private val _dateLive = MutableLiveData(date)
    internal val dateLive: LiveData<DateRange?> = _dateLive

    fun reset() {
        _status.value = ReservationStatus.CONFLICT_NONE
    }

    private fun checkReservation() = viewModelScope.launch {
        val reservations = reservationRepository.fetchSuspended()
        reservations.forEach {

            if (date?.startDate != null && date?.endDate != null) {
                if ((it.startDate?.isBefore(date?.endDate) == true
                                && it.endDate?.isAfter(date?.startDate) == true)) {
                    when {
                        it.room == room?.roomId ->
                            _status.value = ReservationStatus.CONFLICT_DATE_ROOM
                        it.customer == customer?.customerId ->
                            _status.value = ReservationStatus.CONFLICT_DATE_CUSTOMER
                        else -> _status.value = ReservationStatus.CONFLICT_NONE
                    }
                } else
                    _status.value = ReservationStatus.CONFLICT_NONE
            }
        }
    }

    enum class ReservationStatus {
        CONFLICT_DATE_ROOM,
        CONFLICT_DATE_CUSTOMER,
        CONFLICT_NONE
    }
}
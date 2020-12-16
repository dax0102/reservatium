package me.asayah.reservatium.features.reservation.editor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.asayah.reservatium.database.repository.CustomerRepository
import me.asayah.reservatium.database.repository.ReservationRepository
import me.asayah.reservatium.database.repository.RoomRepository
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.room.Room
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationEditorViewModel @ViewModelInject constructor(
    private val reservationRepository: ReservationRepository
): ViewModel() {

    var room: Room? = null
        set(value) {
            field = value
            _roomLive.value = value
            _roomLive.value = _roomLive.value
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
            field.startDate = startDate
            field.endDate = endDate
            field.numberOfGuests = numberOfGuests
            return field
        }
    var startDate: LocalDate? = null
        set(value) {
            field = value
            _startDateLive.value = value
            checkReservation()
        }
    var endDate: LocalDate? = null
        set(value) {
            field = value
            _endDateLive.value = value
            checkReservation()
        }
    var numberOfGuests: Int = 1

    private var _status = MutableLiveData(ReservationStatus.CONFLICT_NONE)
    internal val status: LiveData<ReservationStatus> = _status

    private val _roomLive = MutableLiveData(room)
    internal val roomLive: LiveData<Room?> = _roomLive

    private val _customerLive = MutableLiveData(customer)
    internal val customerLive: LiveData<Customer?> = _customerLive

    private val _startDateLive = MutableLiveData(startDate)
    internal val startDateLive: LiveData<LocalDate?> = _startDateLive

    private val _endDateLive = MutableLiveData(endDate)
    internal val endDateLive: LiveData<LocalDate?> = _endDateLive

    fun reset() {
        _status.value = ReservationStatus.CONFLICT_NONE
    }

    private fun checkReservation() = viewModelScope.launch {
        val reservations = reservationRepository.fetchSuspended()
        reservations.forEach {

            val reservedStartDate = it.startDate
            val reservedEndDate = it.startDate
            val chosenDate = startDate

            if (chosenDate?.isAfter(reservedStartDate) == true
                    || chosenDate?.isBefore(reservedEndDate) == true) {
                when {
                    it.room == room?.roomId ->
                        _status.value = ReservationStatus.CONFLICT_DATE_ROOM
                    it.customer == customer?.customerId ->
                        _status.value = ReservationStatus.CONFLICT_DATE_CUSTOMER
                    else -> _status.value = ReservationStatus.CONFLICT_NONE
                }
            } else _status.value = ReservationStatus.CONFLICT_NONE
        }
    }

    enum class ReservationStatus {
        CONFLICT_DATE_ROOM,
        CONFLICT_DATE_CUSTOMER,
        CONFLICT_NONE
    }
}
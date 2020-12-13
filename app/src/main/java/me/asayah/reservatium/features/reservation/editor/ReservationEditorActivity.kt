package me.asayah.reservatium.features.reservation.editor

import android.os.Bundle
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.EditorReservationBinding
import me.asayah.reservatium.features.shared.base.BaseActivity

class ReservationEditorActivity: BaseActivity() {
    private lateinit var binding: EditorReservationBinding

    private var requestCode: Int = REQUEST_CODE_INSERT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditorReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.appbarLayout.toolbar)
        setToolbarTitle(R.string.editor_new_reservation)

        if (intent.hasExtra(EXTRA_RESERVATION)) {
            requestCode = REQUEST_CODE_UPDATE
            setToolbarTitle(R.string.editor_edit_reservation)
        }
    }

    companion object {
        const val REQUEST_CODE_INSERT = 2
        const val REQUEST_CODE_UPDATE = 4
        const val EXTRA_RESERVATION = "extra:reservation"
        const val EXTRA_ROOM = "extra:room"
        const val EXTRA_CUSTOMER = "extra:customer"
    }
}
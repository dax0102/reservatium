package me.asayah.reservatium.features.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.databinding.FragmentReservationBinding
import me.asayah.reservatium.features.reservation.editor.ReservationEditorActivity
import me.asayah.reservatium.features.shared.base.BaseFragment

@AndroidEntryPoint
class ReservationFragment: BaseFragment() {
    private var _binding: FragmentReservationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentReservationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionButton.setOnClickListener {
            startActivityForResult(Intent(it.context, ReservationEditorActivity::class.java),
                ReservationEditorActivity.REQUEST_CODE_INSERT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
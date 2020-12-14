package me.asayah.reservatium.features.reservation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.components.custom.ItemDecoration
import me.asayah.reservatium.databinding.FragmentReservationBinding
import me.asayah.reservatium.features.shared.base.BaseFragment

@AndroidEntryPoint
class ReservationFragment: BaseFragment() {
    private var _binding: FragmentReservationBinding? = null

    private val binding get() = _binding!!
    private val reservationAdapter = ReservationAdapter()
    private val viewModel: ReservationViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentReservationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            addItemDecoration(ItemDecoration(context))
            adapter = reservationAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.reservations.observe(viewLifecycleOwner) { reservationAdapter.submitList(it) }
        viewModel.isEmpty.observe(viewLifecycleOwner) { binding.emptyView.isVisible = it }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
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
import me.asayah.reservatium.features.reservation.editor.ReservationEditorActivity
import me.asayah.reservatium.features.shared.base.BaseAdapter
import me.asayah.reservatium.features.shared.base.BaseFragment

@AndroidEntryPoint
class ReservationFragment: BaseFragment(), BaseAdapter.ActionListener {
    private var _binding: FragmentReservationBinding? = null

    private val binding get() = _binding!!
    private val reservationAdapter = ReservationAdapter(this)
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

        when(requestCode) {
            ReservationEditorActivity.REQUEST_CODE_UPDATE -> {

            }
        }
    }

    override fun <T> onActionPerformed(t: T, action: BaseAdapter.ActionListener.Action) {
        if (t is ReservationBundle) {
            when(action) {
                BaseAdapter.ActionListener.Action.SELECT -> {
                    val editorIntent = Intent(context, ReservationEditorActivity::class.java).apply {
                        putExtra(ReservationEditorActivity.EXTRA_RESERVATION, t.reservation)
                        putExtra(ReservationEditorActivity.EXTRA_CUSTOMER, t.customer)
                        putExtra(ReservationEditorActivity.EXTRA_ROOM, t.room)
                    }
                    startActivityForResult(editorIntent,
                            ReservationEditorActivity.REQUEST_CODE_UPDATE)
                }
                BaseAdapter.ActionListener.Action.DELETE -> TODO()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
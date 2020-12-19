package me.asayah.reservatium.features.room.editor

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.components.custom.ItemDecoration
import me.asayah.reservatium.databinding.EditorRoomBinding
import me.asayah.reservatium.features.room.Room
import me.asayah.reservatium.features.room.RoomReservationAdapter
import me.asayah.reservatium.features.shared.base.BaseActivity

@AndroidEntryPoint
class RoomEditorActivity: BaseActivity() {
    private lateinit var binding: EditorRoomBinding

    private val reservationAdapter = RoomReservationAdapter()
    private val viewModel: RoomEditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditorRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.toolbar)

        if (intent.hasExtra(EXTRA_ROOM)) {
            intent.getParcelableExtra<Room>(EXTRA_ROOM)?.also {
                viewModel.room = it
                setToolbarTitle(String.format(getString(R.string.room_concat),
                    it.name))
            }
        }

        with(binding.recyclerView) {
            addItemDecoration(ItemDecoration(context))
            adapter = reservationAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.reservations.observe(this) { reservationAdapter.submitList(it) }
        viewModel.isEmpty.observe(this) { binding.emptyView.isVisible = it }
    }

    companion object {
        const val EXTRA_ROOM = "extra:room"
    }
}
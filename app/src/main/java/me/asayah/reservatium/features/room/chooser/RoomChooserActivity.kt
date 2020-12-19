package me.asayah.reservatium.features.room.chooser

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.components.custom.ItemDecoration
import me.asayah.reservatium.databinding.ChooserRoomBinding
import me.asayah.reservatium.features.room.Room
import me.asayah.reservatium.features.room.RoomAdapter
import me.asayah.reservatium.features.shared.base.BaseActivity
import me.asayah.reservatium.features.shared.base.BaseAdapter

@AndroidEntryPoint
class RoomChooserActivity: BaseActivity(), BaseAdapter.ActionListener {
    private lateinit var binding: ChooserRoomBinding

    private val roomAdapter = RoomAdapter(this)
    private val viewModel: RoomChooserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChooserRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.appbarLayout.toolbar)
        setToolbarTitle(R.string.chooser_room)

        with(binding.recyclerView) {
            addItemDecoration(ItemDecoration(context))
            adapter = roomAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.rooms.observe(this) { roomAdapter.submitList(it) }
    }

    override fun <T> onActionPerformed(t: T, action: BaseAdapter.ActionListener.Action) {
        if (t is Room) {
            when(action) {
                BaseAdapter.ActionListener.Action.SELECT -> {
                    setResult(RESULT_OK, Intent().apply {
                        putExtra(EXTRA_ROOM, t)
                    })
                    finish()
                }
                BaseAdapter.ActionListener.Action.DELETE -> { }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_CHOOSE = 6
        const val EXTRA_ROOM = "extra:room"
    }
}
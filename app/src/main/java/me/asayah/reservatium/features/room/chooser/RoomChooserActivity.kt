package me.asayah.reservatium.features.room.chooser

import android.os.Bundle
import me.asayah.reservatium.databinding.ChooserRoomBinding
import me.asayah.reservatium.features.shared.base.BaseActivity

class RoomChooserActivity: BaseActivity() {
    private lateinit var binding: ChooserRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChooserRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopAppBar(binding.appbarLayout.toolbar)
    }

    companion object {
        const val REQUEST_CODE_CHOOSE = 6
        const val EXTRA_ROOM = "extra:room"
    }
}
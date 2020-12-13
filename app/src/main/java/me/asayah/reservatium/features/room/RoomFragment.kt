package me.asayah.reservatium.features.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.FragmentRoomBinding
import me.asayah.reservatium.features.shared.base.BaseFragment

@AndroidEntryPoint
class RoomFragment: BaseFragment() {
    private var _binding: FragmentRoomBinding? = null

    private val roomAdapter = RoomAdapter()
    private val binding get() = _binding!!
    private val viewModel: RoomViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentRoomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            adapter = roomAdapter
        }

        viewModel.rooms.observe(viewLifecycleOwner) { roomAdapter.submitList(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
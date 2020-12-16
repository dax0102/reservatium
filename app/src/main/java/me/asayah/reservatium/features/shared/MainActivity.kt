package me.asayah.reservatium.features.shared

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import me.asayah.reservatium.R
import me.asayah.reservatium.databinding.ActivityMainBinding
import me.asayah.reservatium.features.customer.Customer
import me.asayah.reservatium.features.customer.CustomerViewModel
import me.asayah.reservatium.features.customer.editor.CustomerEditorActivity
import me.asayah.reservatium.features.reservation.Reservation
import me.asayah.reservatium.features.reservation.ReservationViewModel
import me.asayah.reservatium.features.reservation.editor.ReservationEditorActivity
import me.asayah.reservatium.features.shared.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    private val reservationViewModel: ReservationViewModel by viewModels()
    private val customerViewModel: CustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbarLayout.toolbar)

        val appbarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_reservations,
            R.id.navigation_customers,
            R.id.navigation_rooms
        ).build()

        supportFragmentManager.findFragmentById(R.id.navigationHost)?.run {
            controller = findNavController().also {
                binding.appbarLayout.toolbar.setupWithNavController(it, appbarConfiguration)
                ExpandableBottomBarNavigationUI.setupWithNavController(binding.bottomBar, it)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.actionButton.setOnClickListener {
            when(controller.currentDestination?.id) {
                R.id.navigation_reservations -> {
                    startActivityForResult(Intent(this, ReservationEditorActivity::class.java),
                        ReservationEditorActivity.REQUEST_CODE_INSERT)
                }
                R.id.navigation_customers -> {
                    startActivityForResult(Intent(this, CustomerEditorActivity::class.java),
                        CustomerEditorActivity.REQUEST_CODE_INSERT)
                }
            }
        }
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id != R.id.navigation_rooms)
            binding.actionButton.show()
        else binding.actionButton.hide()
    }

    override fun onSupportNavigateUp(): Boolean = false

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        controller.removeOnDestinationChangedListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        when(requestCode) {
            ReservationEditorActivity.REQUEST_CODE_INSERT -> {
                data?.getParcelableExtra<Reservation>(ReservationEditorActivity.EXTRA_RESERVATION)?.also {
                    reservationViewModel.insert(it)
                }
            }
            CustomerEditorActivity.REQUEST_CODE_INSERT -> {
                data?.getParcelableExtra<Customer>(CustomerEditorActivity.EXTRA_CUSTOMER)?.also {
                    customerViewModel.insert(it)
                }
            }
        }
    }
}
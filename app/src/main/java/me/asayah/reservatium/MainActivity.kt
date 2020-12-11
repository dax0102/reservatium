package me.asayah.reservatium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import me.asayah.reservatium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(R.id.navigationHostFragment)?.run {
            findNavController().also {
                ExpandableBottomBarNavigationUI.setupWithNavController(binding.navigationView, it)
            }
        }
    }
}
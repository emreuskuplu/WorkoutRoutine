package com.emre.android.workoutroutine.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.databinding.ActivityMainBinding
import com.emre.android.workoutroutine.view.popupwindows.MainPopupWindow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val popupWindow = MainPopupWindow(binding.mainActivity)

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.currentDestination?.let {
            binding.bottomNavigationView.menu.findItem(it.id).setEnabled(false)
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            it.isEnabled = false

            for (i in 0 until binding.bottomNavigationView.menu.size) {
                if (binding.bottomNavigationView.menu[i] !=
                    binding.bottomNavigationView.menu.findItem(it.itemId)
                ) {
                    binding.bottomNavigationView.menu[i].isEnabled = true
                }
            }

            navController.navigate(it.itemId)
            true
        }

        binding.menuButton.setOnClickListener {
            popupWindow.showAsDropDown(binding.menuButton, 0, -36)
        }

        popupWindow.binding.workouts.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.binding.archive.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.binding.importExport.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.binding.settings.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.binding.help.setOnClickListener {
            popupWindow.dismiss()
        }
    }
}
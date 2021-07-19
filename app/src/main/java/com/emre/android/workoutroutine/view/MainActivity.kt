package com.emre.android.workoutroutine.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.databinding.ActivityMainBinding
import com.emre.android.workoutroutine.view.popupwindows.MainPopupWindow
import com.emre.android.workoutroutine.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val popupWindow = MainPopupWindow(binding.mainActivity)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.bottomBarLiveData.observe(this, {
            if (it == true) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        })

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

            navController.popBackStack(R.id.body_fragment, true)
            navController.popBackStack(R.id.history_fragment, true)
            navController.navigate(it.itemId)
            if (navController.currentDestination?.id == R.id.workout_pager) {
                // It must be called after navigate. It is use for prevent multiple backstack of workout_pager
                navController.popBackStack()
            }
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

    override fun onUserInteraction() {
        viewModel.onUserInteractionLiveData.value = Unit
        super.onUserInteraction()

    }
}
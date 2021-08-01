package com.emre.android.workoutroutine.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.databinding.ActivityMainBinding
import com.emre.android.workoutroutine.view.popupwindows.MainPopupWindow
import com.emre.android.workoutroutine.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val popupWindow = MainPopupWindow(binding.mainActivity)
        setContentView(binding.root)
        navController = navHostFragment.navController
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.toolbarLiveData.observe(this, {
            if (it == true) {
                binding.toolbar.visibility = View.VISIBLE
            } else {
                binding.toolbar.visibility = View.GONE
            }
        })
        viewModel.toolbarBackButtonLiveData.observe(this, {
            if (it == true) {
                binding.backIB.visibility = View.VISIBLE
            } else {
                binding.backIB.visibility = View.GONE
            }
        })
        viewModel.toolbarTitleLiveData.observe(this, {
            binding.titleTV.text = it
        })
        viewModel.bottomBarLiveData.observe(this, {
            if (it == true) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        })
        binding.run {
            bottomNavigationView.let {
                it.setupWithNavController(navController)
                it.setOnItemSelectedListener { menuItem ->
                    menuItem.isEnabled = false
                    for (i in 0 until binding.bottomNavigationView.menu.size) {
                        if (it.menu[i] != it.menu.findItem(menuItem.itemId)) {
                            it.menu[i].isEnabled = true
                        }
                    }
                    navController.run {
                        popBackStack(R.id.body_fragment, true)
                        popBackStack(R.id.history_fragment, true)
                        navigate(menuItem.itemId)
                        if (currentDestination?.id == R.id.workout_pager) {
                            // It must be called after navigate. It is use for prevent multiple backstack of workout_pager
                            popBackStack()
                        }
                    }
                    true
                }
            }
            menuIB.setOnClickListener {
                popupWindow.showAsDropDown(binding.menuIB, 0, -36)
            }
            backIB.setOnClickListener {
                navController.popBackStack()
            }
        }
        navController.currentDestination?.let {
            binding.bottomNavigationView.menu.findItem(it.id).setEnabled(false)
        }
        popupWindow.binding.run {
            workouts.setOnClickListener {
                popupWindow.dismiss()
            }
            archive.setOnClickListener {
                popupWindow.dismiss()
            }
            importExport.setOnClickListener {
                popupWindow.dismiss()
            }
            settings.setOnClickListener {
                popupWindow.dismiss()
            }
            help.setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }

    override fun onUserInteraction() {
        viewModel.onUserInteractionLiveData.value = Unit
        super.onUserInteraction()
    }
}
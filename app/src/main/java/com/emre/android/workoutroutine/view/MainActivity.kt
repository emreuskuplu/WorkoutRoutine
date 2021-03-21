package com.emre.android.workoutroutine.view

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emre.android.workoutroutine.viewmodel.MainViewModel
import com.emre.android.workoutroutine.viewmodel.MainViewModelFactory
import com.emre.android.workoutroutine.R
import com.emre.android.workoutroutine.view.popupwindows.MainPopupWindow
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class MainActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val menu: ImageButton = findViewById(R.id.main_menu)
        val parent: ViewGroup = findViewById(R.id.main_activity)
        val popupWindow = MainPopupWindow(parent)

        val viewModel = ViewModelProvider(this, MainViewModelFactory(
                popupWindow.getWorkoutsClicksObservable(),
                popupWindow.getArchiveClicksObservable(),
                popupWindow.getImportExportClicksObservable(),
                popupWindow.getSettingsClicksObservable(),
                popupWindow.getHelpClicksObservable())).get(MainViewModel::class.java)

        viewModel.menuWorkoutLiveData.observe(this, {
            popupWindow.dismiss()
        })
        viewModel.menuArchiveLiveData.observe(this, {
            popupWindow.dismiss()
        })
        viewModel.menuImportExportLiveData.observe(this, {
            popupWindow.dismiss()
        })
        viewModel.menuSettingsLiveData.observe(this, {
            popupWindow.dismiss()
        })
        viewModel.menuHelpLiveData.observe(this, {
            popupWindow.dismiss()
        })

        bottomNavigationView.setupWithNavController(navController)

        navController.currentDestination?.let {
            bottomNavigationView.menu.findItem(it.id).setEnabled(false)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            it.isEnabled = false

            for (i in 0 until bottomNavigationView.menu.size) {
                if (bottomNavigationView.menu[i] != bottomNavigationView.menu.findItem(it.itemId)) {
                    bottomNavigationView.menu[i].isEnabled = true
                }
            }

            navController.navigate(it.itemId)
            true
        }

        menu.clicks().subscribe {
            popupWindow.showAsDropDown(menu, 0, -36)
        }.addTo(disposables)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
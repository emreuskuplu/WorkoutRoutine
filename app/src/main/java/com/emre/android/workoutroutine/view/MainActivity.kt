package com.emre.android.workoutroutine.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emre.android.workoutroutine.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val menu: ImageButton = findViewById(R.id.main_menu)

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

        menu.setOnClickListener {
            popupWindow(window.decorView.findViewById(android.R.id.content)).showAsDropDown(it, 0, -36)
        }
    }

    private fun popupWindow(parent: ViewGroup): PopupWindow {
        val windowMain = LayoutInflater.from(parent.context).inflate(R.layout.window_main, parent, false)

        val workouts: TextView = windowMain.findViewById(R.id.workouts)
        val archive: TextView = windowMain.findViewById(R.id.archive)
        val importExport: TextView = windowMain.findViewById(R.id.import_export)
        val settings: TextView = windowMain.findViewById(R.id.settings)
        val help: TextView = windowMain.findViewById(R.id.help)

        val popupWindow = PopupWindow(parent.context)
        popupWindow.contentView = windowMain
        popupWindow.width = parent.resources.getDimensionPixelSize(R.dimen.window_width_five_items)
        popupWindow.height = parent.resources.getDimensionPixelSize(R.dimen.window_height_five_items)
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

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

        return popupWindow
    }

}
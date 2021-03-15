package com.emre.android.workoutroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.size
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

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
    }
}
package com.avito.mymusic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.avito.presentation.UI.ApiTracksFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.apiTracksFragment -> {
                    navController.popBackStack(R.id.apiTracksFragment, false) // Убираем стек
                    navController.navigate(R.id.apiTracksFragment) // Переходим на экран
                    true
                }

                R.id.downloadedTracksFragment -> {
                    navController.popBackStack(R.id.downloadedTracksFragment, false)
                    navController.navigate(R.id.downloadedTracksFragment)
                    true
                }

                else -> false
            }
        }

    }



}
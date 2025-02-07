package com.dicoding.latihan.dicodingeventapp.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.latihan.dicodingeventapp.R
import com.dicoding.latihan.dicodingeventapp.data.datastore.DataStoreManager
import com.dicoding.latihan.dicodingeventapp.data.datastore.dataStore
import com.dicoding.latihan.dicodingeventapp.databinding.ActivityHomeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = DataStoreManager.getInstance(applicationContext.dataStore)

        runBlocking {
            val isDarkMode = dataStore.getThemeSetting().first()
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode == true) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_event_upcoming, R.id.navigation_event_finished, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
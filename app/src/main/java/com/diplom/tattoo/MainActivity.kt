package com.diplom.tattoo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.diplom.tattoo.data.DataUtil
import com.diplom.tattoo.data.User
import com.diplom.tattoo.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initMap()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomBar()
    }

    private fun initMap(){
        MapKitFactory.setApiKey(DataUtil.YANDEX_MAP_API_KEY)
        MapKitFactory.initialize(this)
    }

    private fun initBottomBar(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_navigation, R.id.catalog_navigation, R.id.profile_navigation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
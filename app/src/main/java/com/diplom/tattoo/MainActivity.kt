package com.diplom.tattoo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.diplom.tattoo.databinding.ActivityMainBinding
import com.diplom.tattoo.model.SharedDatabaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SharedDatabaseViewModel

    // TODO: отключить темную тему
    // TODO: отключить горизнтальный режим
    // TODO: добавить админ панель
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        model = ViewModelProvider(this)[SharedDatabaseViewModel::class.java]

        setContentView(binding.root)
        initBottomBar()

    }

    private fun initBottomBar() {
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
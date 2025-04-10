package com.example.kalorisayaci

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kalorisayaci.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.topAppBar)
        
        // Setup Navigation - Düzeltilmiş NavController erişimi
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        val navView: BottomNavigationView = binding.bottomNavigationView
        
        // Configure the top-level destinations (no back button)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_stats,
                R.id.navigation_profile,
                R.id.navigation_settings
            )
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        
        // Setup FAB click listener
        binding.fabAddFood.setOnClickListener {
            // Show add food dialog or navigate to add food screen
            // For now we'll just show a toast message
            android.widget.Toast.makeText(
                this,
                "Add food functionality will be implemented",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
} 
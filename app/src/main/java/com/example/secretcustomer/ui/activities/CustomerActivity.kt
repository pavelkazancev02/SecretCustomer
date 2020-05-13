package com.example.secretcustomer.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.secretcustomer.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBar?.setupWithNavController(navController)
    }
}

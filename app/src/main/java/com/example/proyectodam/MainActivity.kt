package com.example.proyectodam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.example.proyectodam.databinding.ActivityMainBinding
import com.example.proyectodam.data.api.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        sessionManager = SessionManager(this)

        setSupportActionBar(binding.appBarMain.toolbar)

        updateNavHeader()

        Log.d("SessionDataMain", "Nombre: ${sessionManager.fetchUserName()}, Email: ${sessionManager.fetchUserEmail()}")


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val startDestination = intent.getStringExtra("startDestination")
        if (startDestination == "nav_shop") {
            navController.navigate(R.id.nav_shop)
        }


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_carrito, R.id.nav_profile, R.id.nav_shop, R.id.nav_history
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    sessionManager.clearSession()
                    drawerLayout.closeDrawers()

                    // ✅ Reiniciar completamente la actividad para limpiar todo
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                    true
                }

                else -> {
                    val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                    if (handled) drawerLayout.closeDrawers()
                    handled
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        // Cada vez que regresa a MainActivity actualiza la info en el header
        updateNavHeader()
    }

    private fun updateNavHeader() {
        val navView = binding.navView
        val headerView = navView.getHeaderView(0)

        val nameTextView = headerView.findViewById<TextView>(R.id.textViewName)
        val emailTextView = headerView.findViewById<TextView>(R.id.textViewEmail)

        val sessionManager = SessionManager(this)
        nameTextView.text = sessionManager.fetchUserName() ?: "Nombre de usuario"
        emailTextView.text = sessionManager.fetchUserEmail() ?: "correo@ejemplo.com"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


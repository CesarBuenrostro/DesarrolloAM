package com.example.proyectodam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
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

        setSupportActionBar(binding.appBarMain.toolbar)

        sessionManager = SessionManager(this)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_carrito, R.id.nav_profile, R.id.nav_shop, R.id.nav_history
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Manejo de item seleccionados del menú
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    sessionManager.clearSession() // Limpiar token
                    drawerLayout.closeDrawers()

                    // Redirigir a LoginFragment
                    navController.navigate(R.id.loginFragment)

                    true
                }
                else -> {
                    // Para navegación normal
                    val handled = androidx.navigation.ui.NavigationUI.onNavDestinationSelected(
                        menuItem,
                        navController
                    )
                    if (handled) {
                        drawerLayout.closeDrawers()
                    }
                    handled
                }
            }
        }
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

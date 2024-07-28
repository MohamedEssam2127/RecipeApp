package com.example.recipeapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class RecipeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
   private lateinit var navController: androidx.navigation.NavController
    private lateinit var toolbar: Toolbar
   // private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)
        drawerLayout = findViewById(R.id.drawer_layout)
       supportActionBar?.title = " HOME"
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_home_host)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.bottom_bar_home -> {
                   navController.navigate(R.id.action_global_homeFragment)
                    // Respond to navigation item 1 reselection
                    supportActionBar?.title = " HOME"
                }
                R.id.bottom_bar_fav -> {
                    navController.navigate(R.id.action_global_favoriteFragment)
                    // Respond to navigation item 2 reselection
                    supportActionBar?.title = " fav"
                }
                R.id.bottom_barchr_sea -> {
                    navController.navigate(R.id.action_global_searchFragment)
                    // Respond to navigation item 3 reselection
                    supportActionBar?.title = " search "
                }
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_sign_out -> {
                finish()
                return true
            }
            R.id.menu_about -> {
                navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}
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

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_home_host)
       supportActionBar?.title = "   HOME"

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
       bottomNavigation.selectedItemId = R.id.bottom_bar_home
       bottomNavigation.setOnItemSelectedListener { item ->
           when(item.itemId) {
               R.id.bottom_bar_home -> {
                   navController.navigate(R.id.action_global_homeFragment)
                   supportActionBar?.title = "   HOME"
                   true
               }
               R.id.bottom_bar_fav -> {
                   navController.navigate(R.id.action_global_favoriteFragment)
                   supportActionBar?.title = "   FAV"
                   true
               }
               R.id.bottom_barchr_sea -> {
                   navController.navigate(R.id.action_global_searchFragment)
                   supportActionBar?.title = "   SEARCH"
                   true
               }
               else -> false
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
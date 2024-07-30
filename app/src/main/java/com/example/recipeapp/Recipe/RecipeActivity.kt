package com.example.recipeapp.Recipe

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
   private lateinit var navController: androidx.navigation.NavController
    private lateinit var toolbar: Toolbar
    companion object {
        var splachFlag = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)


        drawerLayout = findViewById(R.id.drawer_layout)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_home_host)
        supportActionBar?.title = ""

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
       bottomNavigation.selectedItemId = R.id.bottom_bar_home
       bottomNavigation.setOnItemSelectedListener { item ->
           when(item.itemId) {
               R.id.bottom_bar_home -> {
                   navController.navigate(R.id.action_global_homeFragment)
                   toolbar.visibility= View.VISIBLE
                   true
               }
               R.id.bottom_bar_fav -> {
                   toolbar.visibility= View.INVISIBLE
                   navController.navigate(R.id.action_global_favoriteFragment)
                   true
               }
               R.id.bottom_barchr_sea -> {
                   toolbar.visibility= View.INVISIBLE
                   navController.navigate(R.id.action_global_searchFragment)
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
                sharedPreferences = getSharedPreferences("user_id", 0)
                val userId = sharedPreferences.getInt("user_id", -1)
                if (userId != -1) {
                    sharedPreferences.edit().putInt("user_id", -1).apply()
                }

                finish()
               navController.navigate(R.id.action_homeFragment_to_authActivity)

                return true
            }
            R.id.menu_about -> {
                toolbar.visibility= View.INVISIBLE
                navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}
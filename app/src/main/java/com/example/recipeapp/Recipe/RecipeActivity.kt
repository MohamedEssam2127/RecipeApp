package com.example.recipeapp.Recipe

import NetworkLiveData
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.recipeapp.Aucthentication.AuthActivity
import com.example.recipeapp.R
import com.example.recipeapp.models.FavoriteMeal
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: androidx.navigation.NavController
    private lateinit var toolbar: Toolbar
    private lateinit var networkLiveData: NetworkLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)



        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_home_host)

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
                    toolbar.visibility= View.GONE
                    navController.navigate(R.id.action_global_favoriteFragment)
                    true
                }
                R.id.bottom_barchr_sea -> {
                    toolbar.visibility= View.GONE
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
                showLogoutDialog()
                return true
            }
            R.id.menu_about -> {
                toolbar.visibility= View.GONE

//                navController.navigate(R.id.action_homeFragment_to_aboutFragment)
//                return true

                val currentDestination = navController.currentDestination?.id
                when (currentDestination) {
                    R.id.recipeDetailFragment -> {

                        navController.navigate(R.id.action_recipeDetailFragment_to_aboutFragment)
                    }
                    R.id.homeFragment -> {
                        navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    private fun showLogoutDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog, null)
        val cancelBtn: Button = dialogView.findViewById(R.id.btn_dialog_cancel)
        val signoutBtn: Button= dialogView.findViewById(R.id.btn_dialog_signout)


        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        signoutBtn.setOnClickListener {
            sharedPreferences = getSharedPreferences("user_id", 0)
            val userId = sharedPreferences.getInt("user_id", -1)
            if (userId != -1) {
                sharedPreferences.edit().putInt("user_id", -1).apply()
            }
            val currentDestinationid = navController.currentDestination?.id
                when (currentDestinationid) {
                    R.id.recipeDetailFragment -> {

                        navController.navigate(R.id.action_recipeDetailFragment_to_authActivity)
                    }
                    R.id.homeFragment -> {
                        navController.navigate(R.id.action_homeFragment_to_authActivity)
                    }
                }
            finish()

        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    @SuppressLint("MissingSuperCall")


    fun showExitDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_exit, null)
        val cancelBtn: Button = dialogView.findViewById(R.id.btn_dialog_cancel2)
        val exitBtn: Button = dialogView.findViewById(R.id.btn_dialog_exit)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        exitBtn.setOnClickListener {
            dialog.dismiss()
            finishAndRemoveTask()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    fun showRemoveFavDialog(favoriteMeal: FavoriteMeal, onRemove: () -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_fav, null)
        val cancelBtn: Button = dialogView.findViewById(R.id.btn_dialog_cancel3)
        val removeBtn: Button = dialogView.findViewById(R.id.btn_dialog_remove)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        removeBtn.setOnClickListener {
            onRemove()
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }







}
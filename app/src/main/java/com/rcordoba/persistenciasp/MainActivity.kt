package com.rcordoba.persistenciasp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import com.rcordoba.persistenciasp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment : Fragment
    private lateinit var userPrefs : SharedPreferences
    private lateinit var userPrefsEditor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as Fragment

        userPrefs = getPreferences(Context.MODE_PRIVATE)
        userPrefsEditor = userPrefs.edit()

        val color = userPrefs.getInt("fragmentColor",R.color.white)
        changeFragmentColor(color)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_blue -> {
                changeFragmentColor(R.color.black)
                true
            }
            R.id.action_green -> {
                changeFragmentColor(R.color.green)
                true
            }
            R.id.action_red -> {
                changeFragmentColor(R.color.red)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun changeFragmentColor(color:Int){
        navHostFragment.view?.setBackgroundColor(getColor(color))
        saveFragmentColor(color)
    }

    private fun saveFragmentColor(color:Int){
        userPrefsEditor.putInt("fragmentColor",getColor(color)).apply()
    }
}

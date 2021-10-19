package com.udacity.shoestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this).get(ShoeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.navigation)
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if(destination.id == R.id.listFragment) {
                viewModel.clearShoeData()
            }
        }
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.loginFragment,
            R.id.welcomeFragment,
            R.id.listFragment
        ).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.myNavHostFragment.findNavController()
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onNavigateUp(): Boolean {
        val navController = binding.myNavHostFragment.findNavController()
        if(navController.currentDestination?.id == R.id.detailFragment) {
            viewModel.clearShoeData()
            Toast.makeText(this, "clear shoe data", Toast.LENGTH_SHORT).show()
        }
        return super.onNavigateUp()}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.log_out_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logoutItem -> {
                logout()
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        when (binding.myNavHostFragment.findNavController().currentDestination?.id) {
            R.id.loginFragment -> super.onBackPressed()
            R.id.welcomeFragment -> finish()
            R.id.instructionsFragment -> super.onBackPressed()
            R.id.listFragment -> logout()
            R.id.detailFragment -> super.onBackPressed()
            else -> super.onBackPressed()
        }
    }

    private fun logout() {
        viewModel.shoeList.value ?.clear()
        viewModel.clearShoeData() // removes new shoe data for un-added shoes
        val navController = binding.myNavHostFragment.findNavController()
        while(navController.currentDestination?.id != R.id.loginFragment)
        navController.navigateUp()
    }


}

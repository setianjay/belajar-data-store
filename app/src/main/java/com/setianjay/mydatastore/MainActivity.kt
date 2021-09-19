package com.setianjay.mydatastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.setianjay.mydatastore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

    private val preferences by lazy { SettingsPreferences.getInstance(dataStore) }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
        initViewModel()
        setupObserver()
    }

    /* function to initialize listener of view */
    private fun initListener(){
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setThemeSettings(isChecked)
        }
    }

    /* function to initialize view model */
    private fun initViewModel(){
        viewModel = ViewModelProvider(this, MainViewModelFactory(preferences)).get(MainViewModel::class.java)
    }

    /* function subscriber if any have changed */
    private fun setupObserver(){
        // observe for theme data
        viewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive){ // if isDarkModeActive is true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
                Toast.makeText(this@MainActivity, "Dark mode is called", Toast.LENGTH_SHORT).show()
            }else{ // if isDarkModeActive is false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
                Toast.makeText(this@MainActivity, "Light mode is called", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
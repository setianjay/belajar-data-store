package com.setianjay.mydatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: SettingsPreferences): ViewModel() {

    /* call function for get theme data from datastore and convert to LiveData */
    fun getThemeSettings(): LiveData<Boolean>{
        return preferences.getThemeSettings().asLiveData()
    }

    /* call function for set theme data to datastore and pass the argument from here */
    fun setThemeSettings(isDarkModeActive: Boolean){
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }
}
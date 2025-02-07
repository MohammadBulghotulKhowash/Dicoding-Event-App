package com.dicoding.latihan.dicodingeventapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.latihan.dicodingeventapp.data.datastore.DataStoreManager
import kotlinx.coroutines.launch

class SettingViewModel(private val preferences: DataStoreManager): ViewModel() {

    fun getThemeSetting() = preferences.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)
        }
    }
}
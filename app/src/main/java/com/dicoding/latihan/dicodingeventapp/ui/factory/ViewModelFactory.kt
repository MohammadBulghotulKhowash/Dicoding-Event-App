package com.dicoding.latihan.dicodingeventapp.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.latihan.dicodingeventapp.data.datastore.DataStoreManager
import com.dicoding.latihan.dicodingeventapp.ui.setting.SettingViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val dataStoreManager: DataStoreManager): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(dataStoreManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
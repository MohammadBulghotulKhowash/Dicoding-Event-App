package com.dicoding.latihan.dicodingeventapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.latihan.dicodingeventapp.R
import com.dicoding.latihan.dicodingeventapp.data.datastore.DataStoreManager
import com.dicoding.latihan.dicodingeventapp.data.datastore.dataStore
import com.dicoding.latihan.dicodingeventapp.databinding.FragmentSettingBinding
import com.dicoding.latihan.dicodingeventapp.ui.factory.ViewModelFactory

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataStoreManager = DataStoreManager.getInstance(requireActivity().applicationContext.dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(dataStoreManager))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkMode ->
            if (isDarkMode != null) {
                binding.switchTheme.isChecked = isDarkMode
            }

            if (isDarkMode == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
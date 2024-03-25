package com.krisna.diva.githubuser.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Int> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(theme: Int) {
        viewModelScope.launch {
            pref.saveThemeSetting(theme)
        }
    }
}
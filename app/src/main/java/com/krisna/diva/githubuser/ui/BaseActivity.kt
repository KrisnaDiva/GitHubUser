package com.krisna.diva.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.krisna.diva.githubuser.ui.settings.SettingPreferences
import com.krisna.diva.githubuser.ui.settings.SettingsViewModel
import com.krisna.diva.githubuser.ui.settings.SettingsViewModelFactory
import com.krisna.diva.githubuser.ui.settings.dataStore

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref))[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings().observe(this) { themeMode ->
            when (themeMode) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }
}
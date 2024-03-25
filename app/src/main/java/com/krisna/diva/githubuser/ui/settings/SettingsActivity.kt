package com.krisna.diva.githubuser.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krisna.diva.githubuser.R
import com.krisna.diva.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var selectedTheme: Theme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        settingsViewModel.getThemeSettings().observe(this) {
            when (it) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    selectedTheme = Theme.SYSTEM_DEFAULT
                    binding.tvTheme.text = getString(R.string.system_default)
                }

                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    selectedTheme = Theme.LIGHT
                    binding.tvTheme.text = getString(R.string.light)
                }

                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    selectedTheme = Theme.DARK
                    binding.tvTheme.text = getString(R.string.dark)
                }
            }
        }

        binding.layoutTheme.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.choose_theme))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    settingsViewModel.saveThemeSetting(selectedTheme.theme)
                }
                .setSingleChoiceItems(
                    Theme.entries.map { it.displayName }.toTypedArray(),
                    selectedTheme.ordinal
                ) { _, which ->
                    selectedTheme = Theme.entries.toTypedArray()[which]
                }
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
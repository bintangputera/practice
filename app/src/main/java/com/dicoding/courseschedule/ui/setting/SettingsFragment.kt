package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val themeKey = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        val notificationKey = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

        //TODO 10 : Update theme based on value in ListPreference
        themeKey?.setOnPreferenceChangeListener { preference, newValue ->
            val darkModeValues = resources.getStringArray(R.array.dark_mode_value)
            when(newValue)
            {
                darkModeValues[0] -> updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                darkModeValues[1] -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeValues[2] -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        notificationKey?.setOnPreferenceChangeListener { preference, newValue ->
            when(newValue)
            {
                true -> DailyReminder().setDailyReminder(requireContext())
                false -> DailyReminder().cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}
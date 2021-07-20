package com.example.consumerapp.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.consumerapp.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

}
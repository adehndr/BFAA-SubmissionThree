package com.example.submissionthree.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.submissionthree.R

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

}
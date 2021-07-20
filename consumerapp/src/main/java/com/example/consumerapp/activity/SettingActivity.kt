package com.example.consumerapp.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ActivitySettingBinding
import com.example.consumerapp.fragment.SettingFragment
import com.example.consumerapp.AlarmReceiver

class SettingActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private val SWITCHALARM = "switchalarm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        alarmReceiver = AlarmReceiver()
        supportFragmentManager.beginTransaction().add(R.id.activity_setting, SettingFragment())
            .commit()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SWITCHALARM -> {
                if (sharedPreferences != null) {
                    val timeAlarm = "09:00"
                    val message = "It's time for you to check GitHub User App"
                    val alarmSetting: Boolean = sharedPreferences.getBoolean(SWITCHALARM, false)
                    if (alarmSetting) {
                        alarmReceiver.setRepeatAlarm(this, timeAlarm, message)
                    } else {
                        alarmReceiver.cancelAlarm(this)
                    }
                }
            }
        }
    }


}
package com.himanshurawat.preferencehelpersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.himanshurawat.preferencehelper.PreferenceHelper

class SampleActivity: AppCompatActivity(), PreferenceHelper.ChangeListener {
    override fun onPreferenceChanged(key: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val prefHelper = PreferenceHelper.getInstance(this,"userPref")
        prefHelper.add("Key","Program")
        prefHelper.remove("Key")
        Log.i("PreferenceScript",prefHelper.getString("Key","the Nun"))
    }
}
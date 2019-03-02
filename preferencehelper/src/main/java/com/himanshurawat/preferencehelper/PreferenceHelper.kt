package com.himanshurawat.preferencehelper

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper private constructor(){

    companion object {
        private var INSTANCE: PreferenceHelper? = null
        private lateinit var sharedPreferences: SharedPreferences
        private var sharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

        fun getInstance(context: Context,sharedPrefName: String,mode: Int = Context.MODE_PRIVATE): PreferenceHelper{

            INSTANCE = PreferenceHelper()
            if(sharedPrefName.isEmpty()){
                throw IllegalArgumentException("Hey! Name cannot be an Empty String")
            }
            sharedPreferences = context.applicationContext.getSharedPreferences(sharedPrefName,mode)

            return INSTANCE as PreferenceHelper
        }
    }


    fun <T> add(key: String, value: T){
        //If Key is "" type/Empty String Throw an Exception
        checkKey(key)
        when(value){
            is String -> sharedPreferences.edit().putString(key,value as String).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key,value as Boolean).apply()
            is Int -> sharedPreferences.edit().putInt(key,value as Int).apply()
            is Float -> sharedPreferences.edit().putFloat(key,value as Float).apply()
            is Long -> sharedPreferences.edit().putLong(key,value as Long).apply()
        }


    }

    fun addStringSet(key: String,value: Set<String>?){
        checkKey(key)
        sharedPreferences.edit().putStringSet(key,value).apply()
    }

    fun getStringSet(key: String,defValue: Set<String>?):MutableSet<String>?{
        checkKey(key)
        return sharedPreferences.getStringSet(key,defValue)
    }

    fun getString(key: String, defValue: String?): String?{
        checkKey(key)
        return sharedPreferences.getString(key,defValue)
    }

    fun getFloat(key: String, defValue: Float): Float{
        checkKey(key)
        return sharedPreferences.getFloat(key,defValue)
    }

    fun getInt(key: String, defValue: Int): Int{
        checkKey(key)
        return sharedPreferences.getInt(key,defValue)
    }

    fun getLong(key: String, defValue: Long): Long{
        checkKey(key)
        return sharedPreferences.getLong(key,defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean{
        checkKey(key)
        return sharedPreferences.getBoolean(key,false)
    }

    fun contains(key: String): Boolean{
        checkKey(key)
        return sharedPreferences.contains(key)
    }

    fun remove(key: String){
        checkKey(key)
        sharedPreferences.edit().remove(key).apply()
    }

    fun getAll(): Map<String, *>?{
        return sharedPreferences.all
    }

    fun registerPreferenceChangeListener(listener: ChangeListener){
        sharedPreferences.
            registerOnSharedPreferenceChangeListener(getSharedPreferenceChangeListener(listener))
    }

    fun unregisterPreferenceChangeListener(listener: ChangeListener){
        sharedPreferences.
            unregisterOnSharedPreferenceChangeListener(getSharedPreferenceChangeListener(listener))
    }


    interface ChangeListener{
        fun onPreferenceChanged(key: String)
    }


    private fun checkKey(key: String){
        if(key.isEmpty()){
            throw IllegalArgumentException("Uh ho! Empty String Cannot be a Key.")
        }
    }

    private fun getSharedPreferenceChangeListener(listener: ChangeListener): SharedPreferences.OnSharedPreferenceChangeListener{
        if(sharedPreferenceChangeListener == null){
            sharedPreferenceChangeListener =
                SharedPreferences.OnSharedPreferenceChangeListener{ sharedPreferences, key ->
                    listener.onPreferenceChanged(key)
                }
        }
        return sharedPreferenceChangeListener as SharedPreferences.OnSharedPreferenceChangeListener
    }
}



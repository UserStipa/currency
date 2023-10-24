package com.userstipa.currency.data.local

interface Preferences {

    suspend fun setPreferences(key: PreferencesKeys, values: List<String>)

    suspend fun getPreferences(key: PreferencesKeys): List<String>

}
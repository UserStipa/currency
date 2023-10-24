package com.userstipa.currency.data.local

interface Preferences {

    suspend fun setPreferences(key: PreferencesKeys, values: Set<String>)

    suspend fun getPreferences(key: PreferencesKeys): Set<String>

}
package com.example.secretcustomer.util.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val SHARED_PREFS_KEY = "my_dictionary"

private const val SECURE_SHARED_PREFS_KEY = "my_secure_dictionary"

class SharedPreferencesWrapper
constructor(context: Context, isSecure: Boolean) {

    private var preferences: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        preferences = if (isSecure) {
            EncryptedSharedPreferences.create(
                SECURE_SHARED_PREFS_KEY,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        }
    }

    fun set(key: String, value: String) {
        preferences.change { putString(key, value) }
    }

    fun getString(key: String): String? = preferences.getString(key, "")

    fun set(key: String, value: Boolean) {
        preferences.change { putBoolean(key, value) }
    }

    fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)

    fun set(key: String, value: Int) {
        preferences.change { putInt(key, value) }
    }

    fun getInt(key: String): Int = preferences.getInt(key, Int.MIN_VALUE)

    fun set(key: String, value: Long) {
        preferences.change { putLong(key, value) }
    }

    fun getLong(key: String): Long = preferences.getLong(key, Long.MIN_VALUE)

    fun set(key: String, value: Float) {
        preferences.change { putFloat(key, value) }
    }

    fun getFloat(key: String): Float = preferences.getFloat(key, Float.MIN_VALUE)

    fun set(key: String, value: Set<String>) {
        preferences.change { putStringSet(key, value) }
    }

    fun getStringSet(key: String): MutableSet<String>? = preferences.getStringSet(key, emptySet())

    fun clear(key: String) {
        preferences.change { remove(key) }
    }

    fun clearAll() {
        preferences.change { clear() }
    }
}
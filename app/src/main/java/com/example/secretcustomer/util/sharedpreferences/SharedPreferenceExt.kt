package com.example.secretcustomer.util.sharedpreferences

import android.content.SharedPreferences

fun SharedPreferences.change(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.block()
    editor.apply()
}

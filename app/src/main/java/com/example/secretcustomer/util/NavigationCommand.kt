package com.example.secretcustomer.util

import android.content.Intent
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To(val directions: NavDirections): NavigationCommand()
    data class ToIntent(val intent: Intent): NavigationCommand()
    object Back: NavigationCommand()
}
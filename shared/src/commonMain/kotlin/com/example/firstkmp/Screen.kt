package com.example.firstkmp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Tab
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed class Screen(
    val route : String,
    val title : String,
    val icon : ImageVector
) {
    object Home : Screen(
        "home","Home", Icons.Default.Home
    )
    object Apps : Screen(
        "apps","Apps", Icons.Default.Apps
    )
    object Events : Screen(
        "events","Events", Icons.Default.Event
    )
    object Tab : Screen(
        "tab","Tab", Icons.Default.Tab
    )
}

val items = listOf(Screen.Home, Screen.Apps, Screen.Events, Screen.Tab)

@Serializable
data class Detail(val name : String)
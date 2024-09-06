package com.example.scaffoldapp.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.scaffoldapp.R

sealed class MainUnitScreen(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    companion object {
        val screens = listOf(
            Home,
            Info
        )

        const val route_home = "home"
        const val route_info = "info"
        const val route_add = "add"
    }

    private object Home : MainUnitScreen(
        route_home,
        R.string.home,
        Icons.Default.Home
    )

    private object Info : MainUnitScreen(
        route_info,
        R.string.info,
        Icons.Default.Info
    )

    private object Add : MainUnitScreen(
        route_add,
        R.string.add,
        Icons.Default.Add
    )
}
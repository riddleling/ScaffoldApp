package com.example.scaffoldapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.scaffoldapp.screens.Add
import com.example.scaffoldapp.screens.Home
import com.example.scaffoldapp.screens.Info
import com.example.scaffoldapp.screens.MainUnitScreen
import com.example.scaffoldapp.ui.theme.ScaffoldAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldAppTheme {
                MainActivityContent()
            }
        }
    }
}

@Composable
fun MainActivityContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            MainTopBar(navController)
        },
        bottomBar = {
            MainBottomBar(navController)
        },
        floatingActionButton = {
            MainFloatingActionButton(navController) {
                navController.navigate(MainUnitScreen.route_add)
            }
        },
    ) {
        MainUnitNavHost(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val topBarTitle = when (currentDestination?.route) {
        MainUnitScreen.route_home -> stringResource(id = R.string.home)
        MainUnitScreen.route_info -> stringResource(id = R.string.info)
        MainUnitScreen.route_add -> stringResource(id = R.string.add)
        else -> stringResource(id = R.string.app_name)
    }

    TopAppBar(
        title = {
            Text(topBarTitle)
        },
        navigationIcon = {
            if (currentDestination?.route != MainUnitScreen.route_home &&
                currentDestination?.route != MainUnitScreen.route_info &&
                navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun MainBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.route == MainUnitScreen.route_home ||
        currentDestination?.route == MainUnitScreen.route_info) {
        BottomAppBar {
            MainUnitScreen.screens.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination.hierarchy.any { it.route == screen.route },
                    onClick = {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(text = stringResource(id = screen.label))
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = stringResource(id = screen.label)
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }
}

@Composable
fun MainFloatingActionButton(navController: NavHostController, onPressed: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.route == MainUnitScreen.route_home ||
        currentDestination?.route == MainUnitScreen.route_info) {
        FloatingActionButton(
            onClick = onPressed,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}

@Composable
fun MainUnitNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainUnitScreen.route_home,
        modifier = modifier
    ) {
        composable(MainUnitScreen.route_home) {
            Home()
        }
        composable(MainUnitScreen.route_info) {
            Info()
        }
        composable(MainUnitScreen.route_add) {
            Add()
        }
    }
}

@Composable
@Preview
fun MainActivityContentPreview() {
    ScaffoldAppTheme {
        MainActivityContent()
    }
}
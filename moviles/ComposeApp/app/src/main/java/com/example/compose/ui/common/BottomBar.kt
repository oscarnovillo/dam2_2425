package com.example.compose.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compose.ui.navigation.AppDestination
import com.example.compose.ui.navigation.AppMainBottomDestination
import com.example.compose.ui.navigation.CochesDestination


@Composable
fun BottomBar(
    navController: NavController,
    screens: List<AppDestination>,


    ) {


    NavigationBar {
        val state = navController.currentBackStackEntryAsState()

        val currentDestination = state.value?.destination
        screens.forEach { screen ->
            if (screen is AppMainBottomDestination) {
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = null) } ,
                    label = { Text(screen.title) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true,
                    onClick = {
                        //navController.popBackStack(screen.route, inclusive = false)
                        navController.navigate(screen.route) {
//                            // Pop up to the start destination of the graph to
//                            // avoid building up a large stack of destinations
//                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            //restoreState = true
                        }
                    }
                )
            }
        }
    }
}
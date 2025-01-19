package com.example.compose.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.ui.coches.detalle.DetalleCochesScreen
import com.example.compose.ui.coches.listado.ListadoCochesScreen
import com.example.compose.ui.common.BottomBar
import com.example.compose.ui.common.TopBar
import com.example.compose.ui.sumar.SumarScreen
import com.example.compose.ui.users.detalle.DetalleUserScreen
import com.example.compose.ui.users.listado.ListadoUsersScreen
import kotlinx.coroutines.launch


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SumarDestination,

    ) {
        Sumar(navigateToCoches = { navController.navigate(CochesDestination)})
        composable<CochesDestination>
        {
            ListadoCochesScreen(
                onNavigateDetalle = {
                    navController.navigate("${DetalleCoche.route}/$it")
                },
                showSnackbar = { mensaje, onUndo ->

                },

                )

        }
    }

}





@Composable
fun NavigationGenerico()
{
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showSnackbar = { message:String,showUndo: Boolean,undo:()->Unit  ->
        scope.launch {
            if (showUndo) {
                val result = snackbarHostState.showSnackbar(
                    message,
                    actionLabel = "UNDO",
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) {
                    undo()
                }
            }
            else {
                snackbarHostState.showSnackbar(
                    message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }



    val state by navController.currentBackStackEntryAsState()

    val screen = appDestinationList.find { screen ->
        state?.destination?.route == screen.route
    }


    val bottomBar : @Composable () -> Unit ={ BottomBar(
        navController = navController,
        screens = appDestinationList
    ) }
    val topBar : @Composable () -> Unit = { TopBar(
        navController = navController,
        screen = screen
    ) }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        topBar = topBar,
        floatingActionButton = {
            if (screen?.scaffoldState?.fabVisible == true) {
                FloatingActionButton(onClick = { showSnackbar("FAB CLICKED",false, {}) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }

        },

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = SumarDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            Sumar(navigateToCoches = {}, showSnackbar = {showSnackbar(it,false,{})})
            composable(
                route = DetalleCoche.routeWithArgs,
                arguments = DetalleCoche.arguments
            ) {
                DetalleCochesScreen(
                    cocheMatricula = it.arguments?.getString(DetalleCoche.cocheIdArg) ?: "",
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    showSnackbar = {
                       showSnackbar(it,false,{})
                    },
                    )
            }
            composable<CochesDestination>
            {
                ListadoCochesScreen(
                    onNavigateDetalle = {
                        navController.navigate("${DetalleCoche.route}/$it")
                    },
                    showSnackbar = { mensaje,onUndo ->
                        showSnackbar(mensaje,true,onUndo)
                    },

                )

            }
            composable(
                ListadoUsers.route
            ) {
                ListadoUsersScreen(

                    onNavigateDetalle = {
                        navController.navigate("${DetalleUser.route}/$it")
                    },
                    showSnackbar = {
                        showSnackbar(it,false,{})
                    },

                )

            }
            composable(
                route = DetalleUser.routeWithArgs,
                arguments = DetalleUser.arguments
            ) {
                DetalleUserScreen(
                    userId = it.arguments?.getString(DetalleUser.userIdArg) ?: "",
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    showSnackbar = {mensaje ->
                        showSnackbar(mensaje,false,{})
                    },
                )
            }


        }

    }

}



fun NavGraphBuilder.Sumar(
    navigateToCoches : () -> Unit,
    showSnackbar: (String) -> Unit = {}
) {
    composable<SumarDestination>(
    ) {
        SumarScreen(navigateToCoches = navigateToCoches,
            showSnackbar = showSnackbar
        )
    }
}



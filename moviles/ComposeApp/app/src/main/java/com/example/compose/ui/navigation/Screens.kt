package com.example.compose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument




val appDestinationList = listOf(Sumar,ListadoCoches,DetalleCoche,ListadoUsers,DetalleUser)
interface AppDestination{
    val route: Any
    val title: String
    val scaffoldState: ScaffoldState
        get() = ScaffoldState()

}

interface AppMainBottomDestination : AppDestination {
    val onBottomBar: Boolean
    val icon: ImageVector

}

object Sumar : AppMainBottomDestination {
    override val route = SumarDestination
    override val title = "Sumar"
    override val onBottomBar = true
    override val icon = Icons.Filled.Favorite
    override val scaffoldState = ScaffoldState(
        topBarState =TopBarState(showNavigationIcon = false, arrangement = Arrangement.Start),
        fabVisible = true
    )
}

object ListadoCoches : AppMainBottomDestination {
    override val route = CochesDestination
    override val title = "Coches"
    override val onBottomBar = true
    override val icon = Icons.Rounded.AddCircle
}

object ListadoUsers : AppMainBottomDestination {
    override val route = "listadoUsers"
    override val title = "Users"
    override val onBottomBar = true
    override val icon = Icons.Outlined.Build
}

object DetalleCoche : AppDestination {
    override val route = "detalleCoche"
    override val title = "Detalle Coche"
    const val cocheIdArg = "cocheId"
    val routeWithArgs = "$route/{$cocheIdArg}"
    val arguments = listOf(
        navArgument(cocheIdArg) { type = NavType.StringType }
    )
}

object DetalleUser : AppDestination {
    override val route = "detalleUser"
    override val title = "Detalle User"
    const val userIdArg = "userId"
    val routeWithArgs = "${route}/{$userIdArg}"
    val arguments = listOf(
        navArgument(userIdArg) { type = NavType.StringType }
    )
}




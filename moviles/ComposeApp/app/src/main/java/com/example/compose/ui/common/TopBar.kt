package com.example.compose.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compose.ui.navigation.AppDestination
import com.example.compose.ui.navigation.Sumar
import com.example.compose.ui.navigation.TopBarState
import com.example.compose.ui.sumar.SumarScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    screen: AppDestination?,

) {

    screen?.let {destination ->
        TopAppBar(

            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = destination.scaffoldState.topBarState.arrangement
                ) {
                    Text(
                        text = destination.title,
                    )
                }

            },
            navigationIcon = {

                if (destination.scaffoldState.topBarState.showNavigationIcon) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }

            },
            actions = destination.scaffoldState.topBarState.actions,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        )
    }

}
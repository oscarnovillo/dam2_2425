package com.example.compose.ui.users.detalle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.domain.modelo.User
import com.example.compose.ui.coches.detalle.DetalleContract
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.users.detalle.DetalleUsersContract.DetalleUsersEvent

@Composable
fun DetalleUserScreen(
    userId: String,
    detalleUserViewModel: DetalleUsersViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    showSnackbar: (String) -> Unit = {},



    ) {

    val uiState by detalleUserViewModel.uiState.collectAsStateWithLifecycle()



    DetalleUserContent(
        user = uiState.user,
        onDelete = { detalleUserViewModel.handleEvent(DetalleUsersEvent.DelUser) },

        )

    LaunchedEffect(uiState.uiEvent) {
        uiState.uiEvent?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
            } else if (it is UiEvent.Navigate) {
                onNavigateBack()
            }
            detalleUserViewModel.handleEvent(DetalleUsersEvent.UiEventDone)
        }
    }


}

@Composable
fun DetalleUserContent(
    user: User?,
    onDelete: () -> Unit
) {
        Box() {
            Text("Detalle de user ${user?.id}",
                modifier = Modifier.clickable { onDelete() })
        }



}

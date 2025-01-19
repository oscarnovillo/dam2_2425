package com.example.compose.ui.users.listado

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.compose.domain.modelo.User
import com.example.compose.ui.common.SwipeToDeleteContainer
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.users.listado.ListadoContract.ListadoEvent


@Composable
fun ListadoUsersScreen(
    listadoViewModel: ListadoViewModel = hiltViewModel(),

    onNavigateDetalle: (Int) -> Unit = {},
    showSnackbar: (String) -> Unit = {},


    ) {
    val uiState by listadoViewModel.uiState.collectAsState()



    LaunchedEffect(Unit) {
        listadoViewModel.handleEvent(ListadoEvent.GetUsers)
    }

    ListadoContent(
        users = uiState.users,
        isLoading = uiState.isLoading,

        onNavigateDetalle = onNavigateDetalle,

        onDeleteUser = { user -> listadoViewModel.handleEvent(ListadoEvent.DeleteUser(user)) }

    )

    LaunchedEffect(uiState.uiEvent) {
        uiState.uiEvent?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
            }
            listadoViewModel.handleEvent(ListadoEvent.UiEventDone)
        }
    }

}

@Composable
fun ListadoContent(
    users: List<User>,
    isLoading: Boolean,
    onNavigateDetalle: (Int) -> Unit,

    onDeleteUser: (User) -> Unit = {},

    ) {

    if (isLoading) {
        Box(
            modifier = Modifier

                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,

                )
        }
    } else {
        LazyColumn(
            modifier = Modifier

                .fillMaxSize()
        ) {

            this.items(items = users, key = { user -> user.id }) { user ->
                UserItem(
                    user = user,
                    onNavigateDetalle = onNavigateDetalle,
                    onDeleteUser = onDeleteUser
                )
            }

        }
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserItem(
    user: User,
    onNavigateDetalle: (Int) -> Unit,
    onDeleteUser: (User) -> Unit
) {
    SwipeToDeleteContainer(item = user, onDelete = onDeleteUser) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(1.dp)
                .combinedClickable(
                    onClick = { onNavigateDetalle(user.id) },
                    onLongClick = { },
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .weight(0.2F),


                    text = user.id.toString()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.7F)
                        .padding(4.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = user.name
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = user.company,

                        )
                }
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp),
                    model = user.fotoUrl,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = null
                )
            }
        }

    }
}

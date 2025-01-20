package com.example.compose.ui.sumar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers

@Composable
fun SumarScreen(

    sumaViewModel: SumaViewModel = hiltViewModel(),
    showSnackbar: (String) -> Unit = {},
    navigateToCoches : () -> Unit,
) {


    val uiState by sumaViewModel.uiState.collectAsStateWithLifecycle()
    val uiError by sumaViewModel.uiError.collectAsStateWithLifecycle(
        initialValue = null,
        context = Dispatchers.Main.immediate,
    )


    SumarContent(
        contador = uiState.contador,
        incremento = uiState.incremento,
        onIncrementoChange = { incremento ->
            sumaViewModel.handleEvent(
                SumaEvent.ChangeIncremento(
                    incremento
                )
            )
        },
        onSumar = { incremento -> sumaViewModel.handleEvent(SumaEvent.Sumar(incremento)) },
        onRestar = { incremento -> navigateToCoches()
                        sumaViewModel.handleEvent(SumaEvent.Restar(incremento)) },
    )

    LaunchedEffect(uiError) {
        uiError?.let {
            showSnackbar(it)
            //sumaViewModel.handleEvent(SumaEvent.ErrorMostrado)
        }
    }

}

@Composable
fun SumarContent(
    contador: Int,
    incremento: Int,
    onIncrementoChange: (Int) -> Unit,
    onSumar: (Int) -> Unit = {},
    onRestar: (Int) -> Unit = {},

    ) {


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {


        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()

        ) {
            var incrementoLocal by remember {
                mutableStateOf(1)
            }

            Text(
                text = contador.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Button(onClick = { onSumar(incrementoLocal) }) {
                    Text(
                        text = "Sumar"
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = { onRestar(incrementoLocal) }) {
                    Text(
                        text = "Restar"
                    )
                }
            }


            TextField(
                value = incrementoLocal.toString(),
                onValueChange = { incrementoLocal = it.toInt() })


        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    fontScale = 1.5f,
    name = "Light Mode"

)
@Composable
fun PreviewSumarScreen() {

    var uiState = SumaState(1, 1, null)
    SumarContent(contador = 21, incremento = 10, onIncrementoChange = {}, onSumar = { })
}
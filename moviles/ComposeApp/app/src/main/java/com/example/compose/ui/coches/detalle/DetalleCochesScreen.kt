package com.example.compose.ui.coches.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.domain.modelo.Coche
import com.example.compose.ui.coches.detalle.DetalleContract.DetalleEvent
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.theme.ComposeAppTheme
import java.time.LocalDate


@Composable
fun DetalleCochesScreen(
    cocheMatricula: String,
    detalleViewModel: DetalleViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    showSnackbar: (String) -> Unit = {},

    ) {
    val uiState by detalleViewModel.uiState.collectAsStateWithLifecycle()
    val userName by detalleViewModel.userName.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detalleViewModel.handleEvent(DetalleEvent.GetCoche(cocheMatricula))
    }

    DetalleContent(
        coche = uiState.coche,
        onDelete = { detalleViewModel.handleEvent(DetalleEvent.DelCoche) },
        username = userName,


        )

    LaunchedEffect(uiState.uiEvent) {
        uiState.uiEvent.forEach {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
            } else if (it is UiEvent.Navigate) {
                onNavigateBack()
            }
            detalleViewModel.handleEvent(DetalleEvent.UiEventDone)
        }
    }

}

@Composable
fun DetalleContent(
    coche: Coche?,
    username: String,
    onDelete: () -> Unit = {},
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(){
            Spacer(modifier = Modifier.weight(0.12f))
            Row(modifier = Modifier.weight(0.88f)) {
                Spacer(modifier = Modifier.weight(0.07f))
                Column(
                    modifier = Modifier.weight(0.93f)
                    .fillMaxHeight()
                    ) {
                    Text(text = username)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${coche?.matricula}",
                        style= MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${coche?.modelo}",
                        style= MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${coche?.marca}",
                        style= MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp),
                        )
                    Text("${coche?.color}",
                        style= MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = onDelete) {
            Text("Borrar")
        }
    }


}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device= Devices.PIXEL_7_PRO,
    name = "Light Mode"
)
@Composable
fun PreviewDetalleCoche() {

    ComposeAppTheme {
        DetalleContent(
            coche = Coche(
                matricula = "1234aa",
                marca = "Seat",
                modelo = "Ibiza",
                color = "rojo",
                precio = 10000.0,
                fechaMatriculacion = LocalDate.now(),
                km = 100
            ),
            onDelete = {},
            username = "",
        )
    }
}
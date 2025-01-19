package com.example.compose.ui.coches.listado

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.compose.domain.modelo.Coche
import com.example.compose.ui.common.SwipeToDeleteContainer
import com.example.compose.ui.common.UiEvent
import java.time.LocalDate


@Composable
fun ListadoCochesScreen(
    listadoViewModel: ListadoViewModel = hiltViewModel(),
    showSnackbar: (String,()->Unit) -> Unit ,
    onNavigateDetalle: (String) -> Unit = {},


    ) {
    var ui = rememberSaveable() { }
    val uiState by listadoViewModel.uiState.collectAsState()
    var undo by remember { mutableStateOf(false) }


    ListadoContent(
        coches = uiState.coches,
        undo = undo,

        onNavigateDetalle = onNavigateDetalle,

        onDeleteCoche = { coche -> listadoViewModel.handleEvent(ListadoEvent.DeleteCoche(coche)) }

    )

    LaunchedEffect(uiState.event) {
        uiState.event?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message) {
                    listadoViewModel.handleEvent(ListadoEvent.UndoDeleteCoche)
                    undo = true
                }
            }
            listadoViewModel.handleEvent(ListadoEvent.UiEventDone)
        }
    }

}

@Composable
fun ListadoContent(
    coches: List<Coche>,
    onNavigateDetalle: (String) -> Unit,

    onDeleteCoche: (Coche) -> Unit,
    undo: Boolean,

    ) {

    var background2 : Color
    val configuration = LocalConfiguration.current
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        background2 = Color.Magenta
    }
    else{
        background2 = Color.Yellow
    }
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val backgroundColor =
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> Color.Black
            WindowWidthSizeClass.MEDIUM -> Color.Green
            else -> Color.Blue
        }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)

    ) {

        this.items(items = coches, key = { coche -> coche.matricula }) { coche ->
            CocheItem(
                coche = coche,
                onNavigateDetalle = onNavigateDetalle,
                onDeleteCoche = onDeleteCoche,
                undo = undo,
            )
        }

    }


}

@Composable
fun CocheItem(
    coche: Coche,
    onNavigateDetalle: (String) -> Unit,
    onDeleteCoche: (Coche) -> Unit,
    undo: Boolean,
) {
    SwipeToDeleteContainer(item = coche, onDelete = onDeleteCoche) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(1.dp)
            .clickable { onNavigateDetalle(coche.matricula) }) {
            Row(
                modifier = Modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),


                    text = coche.matricula
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.7F)
                        .padding(4.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = coche.marca
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = coche.modelo,

                        )
                }
            }
        }

    }
}

@Preview(name = "Landscape Mode", showBackground = true, heightDp = 480, widthDp = 640)
@Preview(name = "Portrait Mode", showBackground = true, device = Devices.PIXEL_4)
annotation class OrientationPreviews



@OrientationPreviews
@Composable
fun PreviewListadoCochesScreen() {
    ListadoContent(
        coches = listOf(
            Coche("1234", "Rojo", "Seat", "red", 111.0, LocalDate.now(), 100),
            Coche("5678", "Azul", "Renault", "blue", 222.0, LocalDate.now(), 200),
            Coche("9876", "Verde", "Ford", "green", 333.0, LocalDate.now(), 300),
        ),
        onNavigateDetalle = {},
        onDeleteCoche = {},
        undo = false,


        )
}



package com.example.compose.ui.coches.listado

import com.example.compose.domain.modelo.Coche

sealed class ListadoEvent {
    class DeleteCoche(val coche: Coche) : ListadoEvent()
    class SelectCoche(val coche: Coche) : ListadoEvent()
    object UndoDeleteCoche : ListadoEvent()
    object DeleteCocheSeleccionados : ListadoEvent()
    class StartSelectMode(val coche:Coche) : ListadoEvent()
    object EndSelectMode : ListadoEvent()

    class ChangeFiltro(val filtro: String) : ListadoEvent()

    object UiEventDone : ListadoEvent()
}
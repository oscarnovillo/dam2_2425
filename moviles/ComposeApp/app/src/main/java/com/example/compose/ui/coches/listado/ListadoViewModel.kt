package com.example.compose.ui.coches.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.modelo.Coche
import com.example.compose.domain.usecases.coches.DelCoche
import com.example.compose.domain.usecases.coches.DelCoches
import com.example.compose.domain.usecases.coches.GetCochesFlow
import com.example.compose.domain.usecases.coches.InsertCoche
import com.example.compose.ui.coches.listado.ListadoEvent
import com.example.compose.ui.coches.listado.ListadoState
import com.example.compose.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SelectMode(
    val selectMode: Boolean = false,
    val cochesSeleccionados: List<Coche> = mutableListOf(),

)


@HiltViewModel
class ListadoViewModel @Inject constructor(
    getCochesFlow: GetCochesFlow,
    val delCoche: DelCoche,
    val delCoches: DelCoches,
    val insertCoche: InsertCoche,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {

    private var cocheBorrado: Coche? = null
    private val _uiEvent = MutableStateFlow<UiEvent?>(null)
    private val _filtro = MutableStateFlow<String?>(null)
    private val _selectMode = MutableStateFlow(SelectMode())


    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ListadoState> =
        combine(
            _filtro.flatMapLatest { getCochesFlow(_filtro.value) },
            _uiEvent,
            _selectMode,
        ) { coches, uiEvent, selectMode ->
            ListadoState(
                coches = coches,
                isLoading = false,
                selectMode = selectMode.selectMode,
                cochesSeleccionados = selectMode.cochesSeleccionados,
                event = uiEvent
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListadoState()
        )


    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.UiEventDone -> {
                _uiEvent.update { null }
            }

            is ListadoEvent.DeleteCoche -> viewModelScope.launch(dispatcher) {
                cocheBorrado = event.coche
                delCoche(event.coche)
                _uiEvent.update { UiEvent.ShowSnackbar("Coche eliminado", "UNDO") }
            }

            ListadoEvent.UndoDeleteCoche -> {
                cocheBorrado?.let {
                    viewModelScope.launch(dispatcher) { insertCoche(it) }
                }
            }

            is ListadoEvent.ChangeFiltro -> _filtro.update { event.filtro }
            ListadoEvent.EndSelectMode -> _selectMode.update { it.copy(selectMode = false, cochesSeleccionados = mutableListOf()) }
            is ListadoEvent.SelectCoche -> {

                if (_selectMode.value.cochesSeleccionados.contains(event.coche)) {
                    _selectMode.update {
                        it.copy(
                            cochesSeleccionados = it.cochesSeleccionados - event.coche
                        )
                    }
                } else _selectMode.update {
                    it.copy(
                        cochesSeleccionados = it.cochesSeleccionados + event.coche,

                        )
                }

            }

            is ListadoEvent.StartSelectMode -> _selectMode.update {
                it.copy(
                    selectMode = true, cochesSeleccionados = mutableListOf(event.coche)
                )
            }

            ListadoEvent.DeleteCocheSeleccionados -> {
                viewModelScope.launch(dispatcher) {
                    delCoches(_selectMode.value.cochesSeleccionados)
                    _selectMode.update {
                        it.copy(
                            selectMode = false,
                            cochesSeleccionados = mutableListOf())
                    }
                    _uiEvent.update { UiEvent.ShowSnackbar("Coches eliminados") }
                }
            }

        }

    }
}
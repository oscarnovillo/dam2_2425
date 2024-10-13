package com.example.viewmodel.ui.pantalladetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel.R
import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.domain.usecases.personas.AddPersonaUseCase
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.viewmodel.ui.common.StringProvider
import com.example.viewmodel.domain.usecases.personas.DeletePersonaUseCase
import com.example.viewmodel.ui.Constantes
import com.example.viewmodel.ui.common.UiEvent
import com.example.viewmodel.ui.pantalllamain.MainState


class DetalleViewModel(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
    private val getPersonas: GetPersonas,
    private val deletePersonaUseCase: DeletePersonaUseCase,

    ) : ViewModel() {

    private var indice = 0
    private val _uiState : MutableLiveData<DetalleState> = MutableLiveData(null)
    val uiState: LiveData<DetalleState> get() = _uiState


    init {
        //_uiState.value = DetalleState(persona = this.getPersonas()[0])
    }

    fun addPersona(persona: Persona) {
        if (!addPersonaUseCase(persona)) {
            _uiState.value = DetalleState(
                persona = _uiState.value.let { persona },
                event = UiEvent.ShowSnackbar(stringProvider.getString(R.string.name)),
            )
            _uiState.value = _uiState
                .value?.copy(event = UiEvent.ShowSnackbar(Constantes.ERROR))
        }
    }

    fun delPersona(persona: Persona?) {
        _uiState.value?.let {
            if (!deletePersonaUseCase(it.persona)) {
                _uiState.value = _uiState
                    .value?.copy(event = UiEvent.ShowSnackbar(Constantes.ERROR))
            } else {
                _uiState.value = _uiState
                    .value?.copy(event = UiEvent.PopBackStack)
            }
        }

    }

    fun getPersonas(id: Int) {
        val personas = getPersonas()

        if (personas.size < id || id < 0) {
            _uiState.value = _uiState.value?.copy(event = UiEvent.ShowSnackbar(Constantes.ERROR))

        } else
            _uiState.value = _uiState.value?.copy(persona = personas[id]) ?: DetalleState(personas[id])


    }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(event = null)
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class DetalleViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPersona: AddPersonaUseCase,
    private val getPersonas: GetPersonas,
    private val deletePersonaUseCase: DeletePersonaUseCase,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetalleViewModel(
                stringProvider,
                addPersona,
                getPersonas,
                deletePersonaUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
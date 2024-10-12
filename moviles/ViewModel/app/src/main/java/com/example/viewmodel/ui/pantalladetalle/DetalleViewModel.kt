package com.example.viewmodel.ui.pantalladetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel.R
import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.domain.usecases.personas.AddPersonaUseCase
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.appnobasica.utils.StringProvider
import com.example.viewmodel.ui.Constantes

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
    private val getPersonas: GetPersonas,
) : ViewModel() {

    private var indice =0
    private val _uiState = MutableLiveData(DetalleState())
    val uiState: LiveData<DetalleState> get() = _uiState


    init {
        _uiState.value = DetalleState(persona=this.getPersonas()[0])
    }

    fun addPersona(persona: Persona) {
        if (!addPersonaUseCase(persona)) {
            _uiState.value = DetalleState(
                persona = _uiState.value.let{persona},
                error = stringProvider.getString(R.string.name),
            )
            _uiState.value = _uiState
                .value?.copy(error = Constantes.ERROR)
        }
    }

    fun getPersonas(id: Int) {
        val personas = getPersonas()

        if (personas.size < id || id < 0) {
            _uiState.value = _uiState.value?.copy(error = "error")

        } else
            _uiState.value = _uiState.value?.copy(persona = personas[id])


    }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPersona: AddPersonaUseCase,
    private val getPersonas: GetPersonas,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addPersona,
                getPersonas,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
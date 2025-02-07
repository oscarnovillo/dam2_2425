package com.example.compose.ui.coches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.PreferencesRepository
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.usecases.coches.DelCoche
import com.example.compose.domain.usecases.coches.GetCoche
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PreferenceViewModel @Inject constructor(

    private val preferencesRepository: PreferencesRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val userName: StateFlow<String> = preferencesRepository.userName
        .stateIn(viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "")


    fun saveUserName(userName:String) {
        viewModelScope.launch {
            preferencesRepository.saveUserName(userName)
        }
    }


}
package com.example.viewmodel.ui.pantalladetalle

import com.example.viewmodel.domain.modelo.Persona

sealed interface DetalleEvent {


    class AddPersona(val persona: Persona) : DetalleEvent
    class DeletePersona(val persona : Persona) : DetalleEvent
    class GetPersona( val id:Int) : DetalleEvent
    object ErrorMostrado: DetalleEvent
}
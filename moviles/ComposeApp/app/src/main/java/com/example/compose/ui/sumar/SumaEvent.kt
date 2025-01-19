package com.example.compose.ui.sumar

sealed class SumaEvent {
        class Sumar(val incremento:Int) : SumaEvent()
        class Restar(val incremento:Int) : SumaEvent()

        class ChangeIncremento(val incremento:Int) : SumaEvent()
        object ErrorMostrado: SumaEvent()
}
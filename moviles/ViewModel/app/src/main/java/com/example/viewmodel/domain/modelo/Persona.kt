package com.example.viewmodel.domain.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Persona(
    val id : Int = 0,
    val nombre: String,
    var apellidos: String? = null
) : Parcelable



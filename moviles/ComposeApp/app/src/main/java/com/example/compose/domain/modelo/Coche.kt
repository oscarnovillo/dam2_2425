package com.example.compose.domain.modelo

import java.time.LocalDate

data class Coche(
    val matricula: String,
    val marca: String,
    val modelo: String,
    val color: String,
    val precio: Double,
    val fechaMatriculacion: LocalDate,
    val km: Int,
                  )

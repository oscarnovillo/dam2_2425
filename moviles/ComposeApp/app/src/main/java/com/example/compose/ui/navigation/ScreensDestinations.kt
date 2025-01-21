package com.example.compose.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SumarDestination

@Serializable
object CochesDestination

@Serializable
object UsersDestination

@Serializable
data class DetalleCocheDestination(val matricula: String)




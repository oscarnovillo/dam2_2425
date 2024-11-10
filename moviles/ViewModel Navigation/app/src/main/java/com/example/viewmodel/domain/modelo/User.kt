package com.example.viewmodel.domain.modelo

import com.example.primerxmlmvvm.data.remote.modelo.Address
import com.example.primerxmlmvvm.data.remote.modelo.Company

data class User(
    val address: String,
    val company: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String,
    var fotoUrl: String = "https://thispersondoesnotexist.com/",
)
fun User.toPersona() : Persona =
    Persona(id,username,fotoUrl)



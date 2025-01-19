package com.example.compose.domain.modelo

data class User(
    val address: String,
    val company: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String,
    var fotoUrl: String = "https://thispersondoesnotexist.com/?${(0..1000).random()}",
)

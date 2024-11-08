package com.example.viewmodel.data.remote.modelo

import com.example.primerxmlmvvm.data.remote.modelo.Address
import com.example.primerxmlmvvm.data.remote.modelo.Company
import com.example.viewmodel.domain.modelo.User

data class UserRemote(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)

fun UserRemote.toUser() =
    User(
    address = address.toString(),
    company = company.name,
    email = email,
    id = id,
    name = name,
    phone = phone,
    username = username,
    website = website
)


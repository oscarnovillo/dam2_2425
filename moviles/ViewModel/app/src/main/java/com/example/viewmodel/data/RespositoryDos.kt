package com.example.viewmodel.data

import com.example.viewmodel.domain.modelo.Persona

object RespositoryDos {

    private val personas = mutableListOf<Persona>()

    init {
        personas.add(Persona(1,"Juanito"))
        personas.add(Persona(2,"Jorgito"))
        personas.add(Persona(3,"Jaimito"))
    }

    private val mapPersonas = mutableMapOf<String, Persona>()

    fun addPersona(persona: Persona) =
        personas.add(persona.copy(id = personas.size))


    fun getPersona(id : Int) =
        personas.find { persona -> persona.id == id }


    fun getPersonas(): List<Persona> {
        return personas.toList()
    }

    fun delPersona(persona: Persona) =
        personas.remove(persona)
}
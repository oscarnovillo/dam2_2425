package com.example.viewmodel.data

import com.example.viewmodel.domain.modelo.Persona

class Repository {

    fun addPersona(persona: Persona) = personas.add(persona)
    fun delPersona(persona: Persona) = personas.remove(persona)


    fun getPersonas(): List<Persona> {
        return personas
    }

    init {
        personas.add(Persona("Juanito"))
        personas.add(Persona("Jorgito"))
        personas.add(Persona("Jaimito"))
    }

    companion object {

        private val personas = mutableListOf<Persona>()
        fun getInstance(): Repository = Repository()

    }

}
package com.example.viewmodel.data

import com.example.viewmodel.domain.modelo.Persona
import javax.inject.Inject

class Repository @Inject constructor(){

    fun addPersona(persona: Persona) = personas.add(persona)
    fun delPersona(persona: Persona) = personas.remove(persona)


    fun getPersonas(): List<Persona> {
        return personas
    }

    init {
        personas.add(Persona(1,"Juanito"))
        personas.add(Persona(2,"Jorgito"))
        personas.add(Persona(3,"Jaimito"))
    }

    companion object {

        private val personas = mutableListOf<Persona>()
        fun getInstance(): Repository = Repository()

    }

}
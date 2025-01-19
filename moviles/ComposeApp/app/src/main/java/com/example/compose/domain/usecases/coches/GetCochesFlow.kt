package com.example.compose.domain.usecases.coches

import com.example.compose.data.CocheRepository
import javax.inject.Inject

class GetCochesFlow @Inject constructor(val cocheRepository: CocheRepository){

    operator fun invoke(filtro: String?) = cocheRepository.getCochesFlow(filtro)
}
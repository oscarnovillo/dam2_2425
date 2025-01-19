package com.example.compose.domain.usecases.coches

import com.example.compose.data.CocheRepository
import javax.inject.Inject

class GetCoches @Inject constructor(val cocheRepository: CocheRepository){

    operator fun invoke() = cocheRepository.getCoches()
}
package com.example.compose.data.local.modelo

import com.example.compose.domain.modelo.Coche
import com.example.primerxmlmvvm.data.local.modelo.CocheEntity


fun CocheEntity.toCoche(): Coche = Coche(
    matricula = this.matricula,
    marca = this.marca,
    modelo = this.modelo,
    color = this.color,
    precio = this.precio,
    fechaMatriculacion = this.fechaMatriculacion,
    km = this.km
)

fun Coche.toCocheEntity(): CocheEntity = CocheEntity(
    matricula = this.matricula,
    marca = this.marca,
    modelo = this.modelo,
    color = this.color,
    precio = this.precio,
    fechaMatriculacion = this.fechaMatriculacion,
    km = this.km
)

package com.example.primerxmlmvvm.data.local.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose.domain.modelo.Coche
import java.time.LocalDate

@Entity(tableName = "coches")
data class CocheEntity(

    @PrimaryKey
    val matricula: String,
    val marca: String,
    val modelo: String,
    val color: String,
    val precio: Double,
    val fechaMatriculacion: LocalDate,
    val km: Int,
)

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

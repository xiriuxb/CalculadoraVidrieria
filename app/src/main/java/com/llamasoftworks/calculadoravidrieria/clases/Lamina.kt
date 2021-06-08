package com.llamasoftworks.calculadoravidrieria.clases
import kotlinx.serialization.Serializable

@Serializable
data class Lamina(
    var nombre:String,
    var alto: Double,
    var ancho: Double,
    var precioT:Double,
    var precioM:Double
        ){
}
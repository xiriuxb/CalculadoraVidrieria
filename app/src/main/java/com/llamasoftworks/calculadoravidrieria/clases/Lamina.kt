package com.llamasoftworks.calculadoravidrieria.clases
import kotlinx.serialization.Serializable

@Serializable
data class Lamina(
    var nombre:String,
    var alto: Double = 0.0,
    var ancho: Double = 0.0,
    var precioT:Double = 0.0,
    var precioM:Double = 0.0,
    var precioDos:Double = 0.0
        ){
}
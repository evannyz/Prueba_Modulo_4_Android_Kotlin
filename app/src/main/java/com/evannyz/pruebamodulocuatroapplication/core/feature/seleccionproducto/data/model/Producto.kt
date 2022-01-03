package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.model

class Producto(
    val id: Integer,
    val name: String,
    val description: String,
    val urlImage: String,
    val price: Integer,
    var state: Boolean = false
) {
}
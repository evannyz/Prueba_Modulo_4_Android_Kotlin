package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote

import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.model.Producto

data class ProductosResponse(
    val products: List<Producto>
) {
}
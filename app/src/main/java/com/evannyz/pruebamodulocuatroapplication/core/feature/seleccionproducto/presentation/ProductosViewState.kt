package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.presentation

import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.model.Producto

sealed class ProductosViewState {
    data class MostrarProductosViewState(val productos : List<Producto>) : ProductosViewState()
    object MostrarListaVaciaViewState : ProductosViewState()
    object NoHayInternetViewState : ProductosViewState()
    object ServerErrorViewState : ProductosViewState()
}
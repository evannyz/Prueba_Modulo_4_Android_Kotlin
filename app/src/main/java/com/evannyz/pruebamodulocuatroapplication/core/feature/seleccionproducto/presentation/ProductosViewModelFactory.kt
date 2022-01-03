package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote.ProductosRepository

class ProductosViewModelFactory(
    private val repository: ProductosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductosViewModel::class.java)) {
            return ProductosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}
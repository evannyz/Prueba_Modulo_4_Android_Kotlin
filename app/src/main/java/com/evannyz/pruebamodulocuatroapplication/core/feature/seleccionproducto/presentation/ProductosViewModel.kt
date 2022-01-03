package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evannyz.pruebamodulocuatroapplication.core.exception.ServerException
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.model.Producto
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote.ProductosRepository
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote.ProductosResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductosViewModel(
    private val repository: ProductosRepository
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ProductosViewState>()

    fun state(): LiveData<ProductosViewState> = mutableLiveData

    fun obtenerProductos() {
        viewModelScope.launch {
            try {
                val productos = repository.getProductos()
                handleResponse(productos)
            } catch (serverException: ServerException) {
                mutableLiveData.postValue(ProductosViewState.ServerErrorViewState)
            } catch (exception: Exception){
                exception.printStackTrace()
                mutableLiveData.postValue(ProductosViewState.NoHayInternetViewState)
            }
        }
    }

    private fun handleResponse(productos: ProductosResponse) {
            handleProductList(productos.products)
    }

    private fun handleProductList(productos: List<Producto>?) {
        productos?.let { safeProductos ->
            if(safeProductos.isEmpty()){
                mutableLiveData.postValue(ProductosViewState.MostrarListaVaciaViewState)
            } else {
                mutableLiveData.postValue(ProductosViewState.MostrarProductosViewState(safeProductos))
            }
        }
    }
}
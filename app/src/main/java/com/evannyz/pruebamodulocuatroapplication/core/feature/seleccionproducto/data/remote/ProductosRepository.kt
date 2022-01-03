package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote

import com.evannyz.pruebamodulocuatroapplication.core.exception.ServerException

class ProductosRepository(private val remoteApi: ProductosApi) {
    suspend fun getProductos(): ProductosResponse {
        val response = remoteApi.getAll()
        val body = response.body()
        if (body == null) {
            throw ServerException()
        } else {
            return body
        }
    }
}
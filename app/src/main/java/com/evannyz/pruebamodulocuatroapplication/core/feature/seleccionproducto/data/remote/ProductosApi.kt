package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ProductosApi {

    @GET("00cff39e-73e0-4abe-a642-fd9137eac223")
    suspend fun getAll(): Response<ProductosResponse>
}
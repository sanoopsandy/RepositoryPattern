package com.repositorydemo.core.network

import com.repositorydemo.core.cart.response.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    fun fetchCartDetails(): Call<List<ProductResponse>>
}
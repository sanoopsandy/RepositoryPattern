package com.repositorydemo.core.cart.datamanager

import com.repositorydemo.core.network.ApiService
import com.repositorydemo.core.cart.response.ProductResponse
import retrofit2.Call

class CartRemoteHandler(private val service: ApiService) : CartBluePrint.Remote {

    override fun fetchProducts(): Call<List<ProductResponse>> {
        return service.fetchCartDetails()
    }
}
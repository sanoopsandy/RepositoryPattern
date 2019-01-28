package com.repositorydemo.core.cart.datamanager

import android.arch.lifecycle.LiveData
import com.repositorydemo.core.base.api.DataResult
import com.repositorydemo.core.database.models.Product
import com.repositorydemo.core.cart.response.ProductResponse
import retrofit2.Call

interface CartBluePrint {

    interface Repository {
        fun fetchProductList(): LiveData<DataResult<List<Product>>>
        fun saveProducts(products: List<Product>)
    }

    interface Local {

        fun fetchProducts(): LiveData<List<Product>>
        fun saveProducts(products: List<Product>)

    }

    interface Remote {

        fun fetchProducts(): Call<List<ProductResponse>>

    }
}
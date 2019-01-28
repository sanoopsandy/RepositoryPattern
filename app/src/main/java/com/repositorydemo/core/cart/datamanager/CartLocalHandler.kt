package com.repositorydemo.core.cart.datamanager

import android.arch.lifecycle.LiveData
import com.repositorydemo.core.database.AppDb
import com.repositorydemo.core.database.models.Product

class CartLocalHandler(private val appDb: AppDb) : CartBluePrint.Local {
    override fun fetchProducts(): LiveData<List<Product>> {
        return appDb.productDao().getALL()
    }

    override fun saveProducts(products: List<Product>) {
        appDb.productDao().insertAll(products)
    }
}
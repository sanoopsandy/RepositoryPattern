package com.repositorydemo.core.cart.datamanager

import android.arch.lifecycle.LiveData
import com.repositorydemo.core.base.api.DataResult
import com.repositorydemo.core.base.api.NetworkLocalBinder
import com.repositorydemo.core.database.models.Product
import com.repositorydemo.core.cart.response.ProductResponse
import retrofit2.Call

class CartRepository(
    private val local: CartBluePrint.Local,
    private val remote: CartBluePrint.Remote
) : CartBluePrint.Repository {

    override fun fetchProductList(): LiveData<DataResult<List<Product>>> {

        return object : NetworkLocalBinder<List<Product>, List<ProductResponse>>() {
            override fun saveCallResult(item: List<ProductResponse>) {
                item.map {
                    Product(
                        it.productId,
                        it.productMeta.title,
                        it.productMeta.img,
                        it.pricing.amount,
                        it.pricing.deliveryCharge,
                        it.purchaseInstructions.maxPurchaseLimit,
                        1,
                        it.availability.unavailablePincodes
                    )
                }.also { saveProducts(it) }
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                return local.fetchProducts()
            }

            override fun createCall(): Call<List<ProductResponse>> {
                return remote.fetchProducts()
            }

        }.getAsLiveData()
    }

    override fun saveProducts(products: List<Product>) {
        local.saveProducts(products)
    }
}
package com.repositorydemo.core.cart.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.repositorydemo.core.base.api.DataResult
import com.repositorydemo.core.base.DiHandler
import com.repositorydemo.core.cart.datamanager.CartRepository
import com.repositorydemo.core.database.models.Product
import javax.inject.Inject

class CartViewModel : ViewModel() {

    init {
        DiHandler.getCartComponent().inject(this)
    }

    @Inject
    lateinit var repo: CartRepository

    fun getProductList(): LiveData<DataResult<List<Product>>> {
        return repo.fetchProductList()
    }

    override fun onCleared() {
        super.onCleared()
        DiHandler.destroyCartComponent()
    }


}
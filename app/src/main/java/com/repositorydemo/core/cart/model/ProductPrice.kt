package com.repositorydemo.core.cart.model

import android.databinding.BaseObservable

data class ProductPrice(
    var cartPrice: Long,
    var deliveryPrice: Int
) : BaseObservable() {
    val totalPrice
        get(): Long {
            return cartPrice + deliveryPrice
        }
}
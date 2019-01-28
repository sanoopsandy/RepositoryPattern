package com.repositorydemo.core.base

import com.repositorydemo.core.cart.di.CartComponent
import com.repositorydemo.core.cart.di.DaggerCartComponent

object DiHandler {

    private var cartComponent: CartComponent? = null

    /*
    * Fetch the cart component dependency
    * */
    fun getCartComponent(): CartComponent {
        if (cartComponent == null) {
            cartComponent = DaggerCartComponent.builder().coreComponent(CoreApp.coreComponent).build()
        }
        return cartComponent as CartComponent
    }

    fun destroyCartComponent() {
        cartComponent = null
    }

}
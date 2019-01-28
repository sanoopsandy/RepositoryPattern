package com.repositorydemo.core.cart.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.repositorydemo.core.R
import com.repositorydemo.core.base.adapter.BaseRecyclerAdapter
import com.repositorydemo.core.base.api.DataResult
import com.repositorydemo.core.base.DiHandler
import com.repositorydemo.core.cart.model.ProductPrice
import com.repositorydemo.core.cart.di.CartComponent
import com.repositorydemo.core.cart.viewmodel.CartViewModel
import com.repositorydemo.core.databinding.ActivityMainBinding
import com.repositorydemo.core.database.models.Product
import com.repositorydemo.core.utils.showTost
import com.repositorydemo.core.utils.visibilityToggle
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CartActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: BaseRecyclerAdapter

    private val TAG = CartActivity::class.java.simpleName
    private val cartComponent: CartComponent by lazy { DiHandler.getCartComponent() }
    private val viewModel: CartViewModel by lazy { ViewModelProviders.of(this).get(CartViewModel::class.java) }
    private lateinit var products: List<Product>
    private lateinit var productPrice: ProductPrice
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        productPrice = ProductPrice(0, 0)
        binding.price = productPrice
        cartComponent.inject(this)
        products = listOf()
        rvProductList.layoutManager = LinearLayoutManager(this)
        adapter.layoutId = R.layout.row_product_detail
        adapter.items = products
        rvProductList.adapter = adapter
        progress.visibility = View.VISIBLE
        observeAdapterClicks()
        observeProducts()
    }

    private fun observeAdapterClicks() {
        adapter.onCustomClickItemListner = { view, i ->
            val product = products[i]
            when (view.id) {
                R.id.btnAdd -> {
                    if(product.quantity < product.purchaseLimit) {
                        product.quantity = product.quantity + 1
                        productPrice.cartPrice = productPrice.cartPrice + product.amount
                        adapter.notifyItemChanged(i)
                    }else
                        showTost("Max quantity added")
                }
                R.id.btnRemove -> {
                    if (product.quantity > 1) {
                        product.quantity = product.quantity - 1
                        productPrice.cartPrice = productPrice.cartPrice - product.amount
                        adapter.notifyItemChanged(i)
                    }
                }
                else -> {
                }
            }
            binding.price = productPrice
        }
    }

    private fun observeProducts() {
        viewModel.getProductList().observe(this, Observer<DataResult<List<Product>>> { result ->
            when (result) {
                is DataResult.Progress -> {
                    progress.visibilityToggle(result.loading)
                }
                is DataResult.Success -> {
                    progress.visibilityToggle(false)
                    products = result.data
                    Log.i(TAG, "Items -> ${products}")
                    adapter.items = products
                    adapter.notifyDataSetChanged()
                    calculateCartValue()
                    Log.i(TAG, "Inside success")
                }
                is DataResult.Failure -> {
                    progress.visibilityToggle(false)
                    Log.i(TAG, "Inside failure")
                    result.error.message?.let { showTost(it) } ?: showTost("Failure")
                }
            }
        })
    }

    private fun calculateCartValue() {
        productPrice.deliveryPrice = 0
        productPrice.cartPrice = 0
        products.forEach { product ->
            productPrice.deliveryPrice += product.delivery
            productPrice.cartPrice += product.amount
        }
        binding.price = productPrice
    }
}

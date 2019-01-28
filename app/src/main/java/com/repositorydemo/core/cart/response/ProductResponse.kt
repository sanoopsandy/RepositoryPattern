package com.repositorydemo.core.cart.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("availability")
    val availability: Availability,
    @SerializedName("pricing")
    val pricing: Pricing,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_meta")
    val productMeta: ProductMeta,
    @SerializedName("purchase_instructions")
    val purchaseInstructions: PurchaseInstructions
)

data class Pricing(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("delivery_charge")
    val deliveryCharge: Int
)

data class ProductMeta(
    @SerializedName("img")
    val img: String,
    @SerializedName("title")
    val title: String
)

data class PurchaseInstructions(
    @SerializedName("max_purchase_limit")
    val maxPurchaseLimit: Int
)

data class Availability(
    @SerializedName("unavailable_pincodes")
    val unavailablePincodes: List<String>
)
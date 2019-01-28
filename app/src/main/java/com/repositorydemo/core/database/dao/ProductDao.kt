package com.repositorydemo.core.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.repositorydemo.core.database.models.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getALL(): LiveData<List<Product>>

    //TODO: fix
    @Query("SELECT * FROM Product WHERE id = :id AND unavailablePin = :pinCode")
    fun getProductWithPincode(id: Int, pinCode: String): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Delete
    fun delete(product: Product)
}
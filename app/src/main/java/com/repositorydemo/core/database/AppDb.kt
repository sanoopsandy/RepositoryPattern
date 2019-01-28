package com.repositorydemo.core.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.repositorydemo.core.database.dao.ProductDao
import com.repositorydemo.core.database.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
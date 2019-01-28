package com.repositorydemo.core.database.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.support.annotation.NonNull
import com.repositorydemo.core.database.converters.AppTypeConverters

@Entity(indices = [(Index("id"))])
@TypeConverters(AppTypeConverters::class)
data class Product(
    @PrimaryKey
    @NonNull
    val id: String,

    val title: String,

    val img: String,

    val amount: Long,

    val delivery: Int,

    val purchaseLimit: Int,

    var quantity: Int,

    val unavailablePin: List<String>
)
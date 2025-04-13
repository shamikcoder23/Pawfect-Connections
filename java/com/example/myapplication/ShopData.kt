package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Cart")
data class ShopData (
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val image: ByteArray,
    val name: String,
    val price: String
)
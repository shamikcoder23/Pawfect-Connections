package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Tree_data")
data class TreeData (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val image: ByteArray,
    val name: String,
)
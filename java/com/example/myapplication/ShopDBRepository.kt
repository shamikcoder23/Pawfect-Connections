package com.example.myapplication

import androidx.lifecycle.LiveData

class ShopDBRepository(private val dao: ShopDao) {
    val items: LiveData<List<ShopData>> = dao.getRecord()
    val c: Int = dao.getCount()
    suspend fun insertRecord(item: ShopData){
        dao.insertRecord(item)
    }
    suspend fun deleteRecord(item: ShopData){
        dao.deleteRecord(item)
    }
}
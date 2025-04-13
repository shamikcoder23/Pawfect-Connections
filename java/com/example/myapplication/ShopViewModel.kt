package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel(application: Application): AndroidViewModel(application) {
    private val repo: ShopDBRepository
    var items: LiveData<List<ShopData>>
    var c: Int

    init {
        val dao = ShopDB.getDB(application).itemDao()
        repo = ShopDBRepository(dao)
        items = repo.items
        c = repo.c
    }
    fun addItem(item: ShopData) = viewModelScope.launch(Dispatchers.IO) { repo.insertRecord(item) }
    fun remItem(item: ShopData) = viewModelScope.launch(Dispatchers.IO) { repo.deleteRecord(item) }
}
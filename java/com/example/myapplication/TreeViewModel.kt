package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TreeViewModel(application: Application): AndroidViewModel(application) {
    private val repo: TreeDBRepository
    var plants: LiveData<List<TreeData>>

    init {
        val dao = TreeDB.getDB(application).plantDao()
        repo = TreeDBRepository(dao)
        plants = repo.plants
    }
    fun addPlant(plant: TreeData) = viewModelScope.launch(Dispatchers.IO) { repo.insertRecord(plant) }
    fun remPlant(plant: TreeData) = viewModelScope.launch(Dispatchers.IO) { repo.deleteRecord(plant) }
}
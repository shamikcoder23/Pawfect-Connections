package com.example.myapplication

import androidx.lifecycle.LiveData

class TreeDBRepository(private val dao: TreeDao) {
    val plants: LiveData<List<TreeData>> = dao.getRecord()
    suspend fun insertRecord(plant: TreeData){
        dao.insertRecord(plant)
    }
    suspend fun deleteRecord(plant: TreeData){
        dao.deleteRecord(plant)
    }
}
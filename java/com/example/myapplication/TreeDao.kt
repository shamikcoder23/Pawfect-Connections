package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TreeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(plant: TreeData)
    @Query("SELECT * FROM Tree_data GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecord(): LiveData<List<TreeData>>
    @Delete
    suspend fun deleteRecord(plant: TreeData)
}
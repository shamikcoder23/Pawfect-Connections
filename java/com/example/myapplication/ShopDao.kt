package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(item: ShopData)
    @Query("SELECT * FROM Cart GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecord(): LiveData<List<ShopData>>
    @Delete
    suspend fun deleteRecord(item: ShopData)
    @Query("SELECT COUNT(*) FROM Cart")
    fun getCount(): Int
}
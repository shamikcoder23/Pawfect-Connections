package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PetDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(pet: PetData)
    @Query ("SELECT * FROM Pets_data GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecord(): LiveData< List<PetData>>
    @Query ("SELECT DISTINCT * FROM Pets_data WHERE petType = 1 GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecordDog(): LiveData< List<PetData>>
    @Query ("SELECT DISTINCT * FROM Pets_data WHERE petType = 2 GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecordCat(): LiveData< List<PetData>>
    @Query ("SELECT DISTINCT * FROM Pets_data WHERE petType = 3 GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecordBird(): LiveData< List<PetData>>
    @Query ("SELECT DISTINCT * FROM Pets_data WHERE petType = 4 GROUP BY image HAVING COUNT(image) >= 1")
    fun getRecordFish(): LiveData< List<PetData>>
    @Query ("SELECT * FROM Pets_data WHERE category LIKE :cat")
    fun getRecordByCategory(cat: String): LiveData< List<PetData>>
    @Delete
    suspend fun deleteRecord(pet: PetData)
}
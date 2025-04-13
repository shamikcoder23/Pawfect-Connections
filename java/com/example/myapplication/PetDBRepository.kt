package com.example.myapplication

import androidx.lifecycle.LiveData

//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.room.Room
//
//object PetDBProvider {
//    @Volatile
//    private var inst: PetDB? = null
//    fun getDB(context: Context): PetDB {
//        synchronized(this){
//            if(inst == null) {
//                inst =
//                    Room.databaseBuilder(context.applicationContext, PetDB::class.java, "database")
//                        .allowMainThreadQueries().build()
//            }
//            return inst as PetDB
//        }
//    }
//}
////class PetDBProvider (private val context: Context){
////    private val petDataBase = Room.databaseBuilder(context, PetDB::class.java, "db").build()
////    fun insert(pet: PetData){
////        petDataBase.petDao().insertRecord(pet)
////    }
////    fun getPet(value: Int): LiveData<List<PetData>> {
////        return petDataBase.petDao().getRecord(value)
////    }
////}

class PetDBRepository(private val dao: PetDAO) {
    val allPets: LiveData< List<PetData>> = dao.getRecord()
    val allDogs: LiveData< List<PetData>> = dao.getRecordDog()
    val allCats: LiveData< List<PetData>> = dao.getRecordCat()
    val allBirds: LiveData< List<PetData>> = dao.getRecordBird()
    val allFishes: LiveData< List<PetData>> = dao.getRecordFish()

    suspend fun insertRecord(pet: PetData){
        dao.insertRecord(pet)
    }
    suspend fun deleteRecord(pet: PetData){
        dao.deleteRecord(pet)
    }
}
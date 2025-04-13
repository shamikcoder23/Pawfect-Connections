package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [PetData::class], version = 10)
@TypeConverters()
abstract class PetDB: RoomDatabase() {
    abstract fun petDao(): PetDAO

    companion object{
        @Volatile
        var instance: PetDB? = null
        //private val lock = Any()
        fun getMyDB(context: Context): PetDB {
//            if (instance != null) {
//                return instance as PetDB
//            }
//            instance = Room.databaseBuilder(context.applicationContext, PetDB::class.java, "pets").build()
//            return instance as PetDB
            synchronized(this){
                if(instance == null) {
                    instance =
                        Room.databaseBuilder(context.applicationContext, PetDB::class.java, "db")
                            .allowMainThreadQueries().build()
                }
                return instance as PetDB
            }
        }
    }
}
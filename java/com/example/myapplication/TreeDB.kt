package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TreeData::class], version = 1)
@TypeConverters()
abstract class TreeDB: RoomDatabase() {
    abstract fun plantDao(): TreeDao

    companion object{
        @Volatile
        var instance: TreeDB ?= null

        fun getDB(context: Context): TreeDB {
            synchronized(this){
                if(instance == null) {
                    instance =
                        Room.databaseBuilder(context.applicationContext, TreeDB::class.java, "db1")
                            .allowMainThreadQueries().build()
                }
                return instance as TreeDB
            }
        }
    }
}
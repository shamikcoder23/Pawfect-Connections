package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.TreeDB.Companion

@Database(entities = [ShopData::class], version = 1)
@TypeConverters
abstract class ShopDB: RoomDatabase() {
    abstract fun itemDao(): ShopDao

    companion object{
        @Volatile
        var instance: ShopDB ?= null
        fun getDB(context: Context): ShopDB {
            synchronized(this){
                if(instance == null) {
                    instance =
                        Room.databaseBuilder(context.applicationContext, ShopDB::class.java, "db2")
                            .allowMainThreadQueries().build()
                }
                return instance as ShopDB
            }
        }
    }
}
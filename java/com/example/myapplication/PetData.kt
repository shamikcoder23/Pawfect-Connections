package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Pets_data")
data class PetData (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    @ColumnInfo("image", typeAffinity = ColumnInfo.BLOB) val image: ByteArray,
//    @ColumnInfo("name") val name: String? = null,
//    @ColumnInfo("category") val category: String? = null,
//    @ColumnInfo("description") val description: String? = null
    val petType: Int, //1 = Dog, 2 = Cat, 3 = Bird, 4 = Fish
    val image: ByteArray,
    val name: String,
    val category: String
)
// {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as PetData
//
//        if (id != other.id) return false
//        if (!image.contentEquals(other.image)) return false
//        if (name != other.name) return false
//        if (category != other.category) return false
//        if (description != other.description) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = id
//        result = 31 * result + image.contentHashCode()
//        result = 31 * result + (name?.hashCode() ?: 0)
//        result = 31 * result + (category?.hashCode() ?: 0)
//        result = 31 * result + (description?.hashCode() ?: 0)
//        return result
//    }
//}
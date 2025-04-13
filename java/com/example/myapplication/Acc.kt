package com.example.myapplication

data class Acc (private var img : Int, private var name : String, private var price: String) {

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImg(): Int {
        return img
    }

    fun setImg(img: Int) {
        this.img = img
    }

    fun getPrice(): String {
        return price
    }
    fun setPrice(price: String){
        this.price = price
    }
}
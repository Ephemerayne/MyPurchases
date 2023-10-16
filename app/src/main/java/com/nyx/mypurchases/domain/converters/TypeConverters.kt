package com.nyx.mypurchases.domain.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nyx.mypurchases.domain.entity.ProductModel


//TODO
class Converters {

    @TypeConverter
    fun listToJson(value: List<ProductModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<ProductModel>::class.java).toList()
}
package com.emrekizil.movieapp.data.database

import androidx.room.TypeConverter

class StringListTypeConverter {

    @TypeConverter
    fun fromString(value:String):List<String>{
        return value.split(",")
    }

    @TypeConverter
    fun toString(value: List<String>):String{
        return value.joinToString(",")
    }
}
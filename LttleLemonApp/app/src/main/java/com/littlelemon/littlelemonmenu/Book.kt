package com.littlelemon.littlelemonmenu

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
data class Book(
    @SerializedName("pk")
    val name: String,

    @SerializedName("fields")
    val fields: BookFields
)

data class BookFields(
    val author: String,
    val price: String,
    @SerializedName("number_of_pages")
    val numberOfPages: Int,
    val language: String,
    val genre: String,
    val description: String,
    val published: String,
    @SerializedName("cover_image")
    val coverImage: String
)
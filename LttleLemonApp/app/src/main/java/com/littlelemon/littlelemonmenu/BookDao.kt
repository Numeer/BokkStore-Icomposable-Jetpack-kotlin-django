package com.littlelemon.littlelemonmenu

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertBooks(books: List<Book>)
}

package com.littlelemon.littlelemonmenu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Books.db"
        const val TABLE_NAME = "books"
        const val COLUMN_NAME = "name"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_PRICE = "price"
        const val COLUMN_NO_OF_PAGES = "number_of_pages"
        const val COLUMN_LANGUAGE = "language"
        const val COLUMN_GENRE = "genre"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_COVER_IMAGE = "cover_image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME TEXT PRIMARY KEY," +
                "$COLUMN_AUTHOR TEXT," +
                "$COLUMN_PRICE TEXT," +
                "$COLUMN_NO_OF_PAGES INT,"+
                "$COLUMN_LANGUAGE TEXT, "+
                "$COLUMN_GENRE TEXT, "+
                "$COLUMN_DESCRIPTION TEXT, "+
                "$COLUMN_PUBLISHED TEXT, "+
                "$COLUMN_COVER_IMAGE TEXT" +
                ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertBook(book: Book): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, book.name)

            put(COLUMN_AUTHOR, book.fields.author)
            put(COLUMN_PRICE, book.fields.price)
            put(COLUMN_NO_OF_PAGES, book.fields.numberOfPages)
            put(COLUMN_LANGUAGE, book.fields.language)
            put(COLUMN_GENRE, book.fields.genre)
            put(COLUMN_DESCRIPTION, book.fields.description)
            put(COLUMN_PUBLISHED, book.fields.published)
            put(COLUMN_COVER_IMAGE, book.fields.coverImage)
        }
        return db.insert(TABLE_NAME, null, values)
    }


    fun getAllBooks(): List<Book> {
        val books = mutableListOf<Book>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor?.use {
            while (cursor.moveToNext()) {
                val bookName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
                val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val numberOfPages = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NO_OF_PAGES))
                val language = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val published = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHED))
                val coverImage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER_IMAGE))

                val bookFields = BookFields(
                    author, price, numberOfPages, language, genre, description, published, coverImage
                )
                val book = Book(bookName, bookFields)
                books.add(book)
            }
        }
        cursor?.close()
        return books
    }

    fun deleteAllBooks() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}

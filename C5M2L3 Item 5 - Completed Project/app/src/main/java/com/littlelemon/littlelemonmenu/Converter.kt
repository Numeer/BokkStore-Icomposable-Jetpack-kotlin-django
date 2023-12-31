package com.littlelemon.littlelemonmenu
import android.net.Uri
import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return value?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }
}


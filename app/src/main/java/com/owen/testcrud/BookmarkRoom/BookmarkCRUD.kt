package com.owen.testcrud.BookmarkRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkCRUD (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val namaHotel: String,
    val wilayahHotel: String,
    val alamatHotel: String
)

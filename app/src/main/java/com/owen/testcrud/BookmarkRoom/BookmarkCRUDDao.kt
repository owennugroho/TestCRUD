package com.owen.testcrud.BookmarkRoom

import androidx.room.*

@Dao
interface BookmarkCRUDDao {

    @Insert
    suspend fun addBookmark(bookmarkcrud: BookmarkCRUD)

    @Update
    suspend fun updateBookmark(bookmarkcrud: BookmarkCRUD)

    @Delete
    suspend fun deleteBookmark(bookmarkcrud: BookmarkCRUD)

    @Query("SELECT * FROM bookmarkcrud")
    suspend fun getBookmark() : List<BookmarkCRUD>

    @Query("SELECT * FROM bookmarkcrud WHERE id =:bookmarkcrud_id")
    suspend fun getBookmark(bookmarkcrud_id : Int) : List<BookmarkCRUD>
}
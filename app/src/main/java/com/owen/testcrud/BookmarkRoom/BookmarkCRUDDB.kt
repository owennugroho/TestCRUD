package com.owen.testcrud.BookmarkRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkCRUD::class],
    version = 1
)

abstract class BookmarkCRUDDB: RoomDatabase(){

    abstract fun bookmarkCRUDDao() : BookmarkCRUDDao

    companion object{

        @Volatile private var instance : BookmarkCRUDDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BookmarkCRUDDB::class.java,
                "bookmarkCRUD.db"
            ).build()
    }
}
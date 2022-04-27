package com.dheeraj.newsxavier.localdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDAO {
    @Insert
    suspend fun insertArticle(bookmarks: Bookmarks): Long

    @Delete
    suspend fun deleteArticle(bookmarks: Bookmarks)

    @Query("SELECT * FROM Bookmarks ORDER BY  bookmark_id DESC")
    fun getAllBookmarks(): LiveData<List<Bookmarks>>

    @Query("DELETE FROM Bookmarks")
    suspend fun deleteAll()

}
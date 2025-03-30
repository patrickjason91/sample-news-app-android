package com.pjlapps.guardiannews.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_details LIMIT :limit OFFSET :offset")
    fun getNewsList(limit: Int, offset: Int): List<NewsDetail>

    @Query("SELECT * FROM news_details where id = :id")
    fun getNewsById(id: String): NewsDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsList(newsList: List<NewsDetail>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(news: NewsDetail)

    @Delete
    fun deleteNews(news: NewsDetail)
}
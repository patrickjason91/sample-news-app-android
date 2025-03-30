package com.pjlapps.guardiannews.data

import android.util.Log
import com.pjlapps.guardiannews.domain.NewsDetailResult
import com.pjlapps.guardiannews.domain.NewsListResult
import com.pjlapps.guardiannews.network.RemoteDataSource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val appDatabase: AppDatabase
) : NewsRepository {
    val newsDao by lazy { appDatabase.newsDao() }

    override suspend fun getNewsList(
        page: Int,
        pageSize: Int
    ): Result<NewsListResult> {
        try {
            val newsList = remoteDataSource.getNewsList(
                page = page,
                pageSize = pageSize
            )
                .let {
                    val response = it.response
                    response.toNewsListResult()
                }.also {
                    newsDao.insertNewsList(it.newsList)
                }
            return Result.success(newsList)
        } catch (e: Exception) {
            Log.e(TAG, "getNewsListLiveData: error", e)
            val newsList = newsDao.getNewsList(limit = pageSize, offset = getPageOffset(page, pageSize))
            return if (newsList.isEmpty()) {
                Result.failure(EmptyResultException())
            } else {
                val result = NewsListResult(
                    total = 0,
                    startIndex = 0,
                    pageSize = 0,
                    currentPage = 0,
                    pages = 0,
                    newsList = newsList,
                    fromCache = true
                )
                Result.success(result)
            }
        }
    }

    override suspend fun getNewsDetailsById(newsId: String): Result<NewsDetailResult> {
        try {
            val result = remoteDataSource.getNewsItem(newsId)
                .let {
                    val response = it.response
                    response.toNewsDetailResult()
                }
            appDatabase.newsDao().updateNews(result.newsDetail)
            return Result.success(result)
        } catch (e: Exception) {
            Log.e(TAG, "getNewsDetails: error", e)
            val localNewsDetail = newsDao.getNewsById(newsId)
            return if (localNewsDetail != null) {
                val result = NewsDetailResult(localNewsDetail)
                return Result.success(result)
            } else {
                Result.failure(EmptyResultException())
            }
        }
    }

    companion object {
        private const val TAG = "NewsRepository"
    }
}

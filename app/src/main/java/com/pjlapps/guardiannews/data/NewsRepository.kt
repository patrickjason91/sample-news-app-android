package com.pjlapps.guardiannews.data

import com.pjlapps.guardiannews.domain.NewsDetailResult
import com.pjlapps.guardiannews.domain.NewsListResult

/**
 * Primary interface to get news data from the data source
 */
interface NewsRepository {
    /**
     * Get news list
     */
    suspend fun getNewsList(
        page: Int = 1,
        pageSize: Int = 20
    ): Result<NewsListResult>

    /**
     * Get news details by id
     */
    suspend fun getNewsDetailsById(newsId: String): Result<NewsDetailResult>
}
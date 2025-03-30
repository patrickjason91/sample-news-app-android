package com.pjlapps.guardiannews.network

import com.pjlapps.guardiannews.domain.NewsApiDto

/**
 * Interface for news obtained from API
 */
interface RemoteDataSource {
    /**
     * Fetch news list from API
     */
    suspend fun getNewsList(
        pageSize: Int,
        page: Int
    ): NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsListResponseDto>

    /**
     * Fetch news item from API
     */
    suspend fun getNewsItem(
        newsUrl: String
    ): NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsDetailResponseDto>
}
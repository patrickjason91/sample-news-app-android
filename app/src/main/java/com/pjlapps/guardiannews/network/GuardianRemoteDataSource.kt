package com.pjlapps.guardiannews.network

import com.pjlapps.guardiannews.domain.NewsApiDto
import javax.inject.Inject

class GuardianRemoteDataSource @Inject constructor(
    val apiService: GuardianApiService,
    val guardianApiKey: String
) : RemoteDataSource {

    override suspend fun getNewsList(
        pageSize: Int,
        page: Int
    ): NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsListResponseDto> {
        return apiService.getNewsList(
            fields = NEWS_LIST_FIELDS,
            section = "world",
            pageSize = pageSize,
            page = page,
            apiKey = guardianApiKey
        )
    }

    override suspend fun getNewsItem(
        newsUrl: String
    ): NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsDetailResponseDto> {
        return apiService.getNewsDetail(
            newsDetailUrl = newsUrl,
            fields = NEWS_DETAIL_FIELDS,
            apiKey = guardianApiKey
        )
    }

    companion object {
        const val NEWS_LIST_FIELDS = "headline,thumbnail,byline"
        const val NEWS_DETAIL_FIELDS = "headline,thumbnail,byline,body"
    }
}
package com.pjlapps.guardiannews.network

import com.pjlapps.guardiannews.domain.NewsApiDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GuardianApiService {

    @GET(ENDPOINT_SEARCH)
    suspend fun getNewsList(
        @Query("show-fields") fields: String,
        @Query("section") section: String,
        @Query("page-size") pageSize: Int,
        @Query("page") page: Int,
        @Query("api-key") apiKey: String
    ): NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsListResponseDto>

    @GET
    suspend fun getNewsDetail(
        @Url newsDetailUrl: String,
        @Query("show-fields") fields: String,
        @Query("api-key") apiKey: String
    ): NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsDetailResponseDto>

    companion object {
        const val ENDPOINT_SEARCH = "search"
    }
}
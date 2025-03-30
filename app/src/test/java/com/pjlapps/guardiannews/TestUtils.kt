package com.pjlapps.guardiannews

import com.pjlapps.guardiannews.data.NewsDetail
import com.pjlapps.guardiannews.domain.NewsApiDto
import com.pjlapps.guardiannews.domain.NewsListResult

fun createNewsListResult(newsListCount: Int = 1): NewsListResult {
    val newsList = mutableListOf<NewsDetail>()
    repeat(newsListCount) {
        newsList.add(createNewsDetail())
    }
    return NewsListResult(
        total = 100,
        startIndex = 1,
        pageSize = 20,
        currentPage = 1,
        pages = 20,
        newsList = newsList,
        fromCache = false
    )

}

fun createNewsDetail(): NewsDetail {
    return NewsDetail(
        id = "id",
        type = "type",
        sectionId = "sectionId",
        webPublicationDate = "",
        webTitle = "Title",
        webUrl = "url",
        apiUrl = "url",
        headline = "Headline",
        trailText = "trailText",
        byline = "byline",
        thumbnail = "thumbnail",
        body = "",
        createdAt = "",
        updatedAt = ""
    )
}

fun createNewsListResponseDto(): NewsApiDto.NewsListResponseDto {
    return NewsApiDto.NewsListResponseDto(
        status = "ok",
        total = 1,
        startIndex = 1,
        pageSize = 1,
        currentPage = 1,
        pages = 1,
        orderBy = "",
        results = listOf(
            createNewsContentDto()
        )
    )
}

fun createNewsContentDto(
    headline: String = "",
    byline: String = "",
    thumbnail: String = "",
    body: String = ""
): NewsApiDto.NewsContentDto {
    return NewsApiDto.NewsContentDto(
        id = "1",
        type = "article",
        sectionId = "1",
        sectionName = "test",
        webPublicationDate = "2022-01-01",
        webTitle = "test",
        webUrl = "test",
        apiUrl = "test",
        fields = NewsApiDto.NewsContentDto.FieldsDto(
            headline = headline,
            byline = byline,
            thumbnail = thumbnail,
            body = body
        ),
        isHosted = true,
        pillarId = "",
        pillarName = ""
    )
}
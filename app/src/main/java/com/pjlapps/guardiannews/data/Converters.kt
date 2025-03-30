package com.pjlapps.guardiannews.data

import com.pjlapps.guardiannews.domain.NewsApiDto
import com.pjlapps.guardiannews.domain.NewsDetailResult
import com.pjlapps.guardiannews.domain.NewsListResult
import java.util.Calendar

fun NewsApiDto.NewsListResponseDto.toNewsListResult(): NewsListResult {
    val createdDate = Calendar.getInstance().toString()
    val newsList = this.results.map {
        NewsDetail(
            id = it.id,
            type = it.type,
            sectionId = it.sectionId,
            webPublicationDate = it.webPublicationDate,
            webTitle = it.webTitle,
            webUrl = it.webUrl,
            apiUrl = it.apiUrl,
            headline = it.fields.headline,
            byline = it.fields.byline,
            thumbnail = it.fields.thumbnail,
            body = "",
            trailText = "",
            createdAt = createdDate,
            updatedAt = createdDate
        )
    }
    return NewsListResult(
        total = this.total,
        startIndex = this.startIndex,
        pageSize = this.pageSize,
        currentPage = this.currentPage,
        pages = this.pages,
        newsList = newsList
    )
}

fun NewsApiDto.NewsDetailResponseDto.toNewsDetailResult(): NewsDetailResult {
    val createdDate = Calendar.getInstance().toString()
    val newsContent = this.content
    val newsDetail = NewsDetail(
        id = newsContent.id,
        type = newsContent.type,
        sectionId = newsContent.sectionId,
        webPublicationDate = newsContent.webPublicationDate,
        webTitle = newsContent.webTitle,
        webUrl = newsContent.webUrl,
        apiUrl = newsContent.apiUrl,
        headline = newsContent.fields.headline,
        byline = newsContent.fields.byline,
        thumbnail = newsContent.fields.thumbnail,
        body = newsContent.fields.body,
        trailText = "",
        createdAt = createdDate,
        updatedAt = createdDate
    )
    return NewsDetailResult(
        newsDetail
    )
}
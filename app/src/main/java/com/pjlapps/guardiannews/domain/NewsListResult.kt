package com.pjlapps.guardiannews.domain

import com.pjlapps.guardiannews.data.NewsDetail

data class NewsListResult(
    val total: Int,
    val startIndex: Int,
    val pageSize: Int,
    val currentPage: Int,
    val pages: Int,
    val newsList: List<NewsDetail>,
    val fromCache: Boolean = false
)
package com.pjlapps.guardiannews.domain

object NewsApiDto {
    data class NewsApiResponseDto<T>(
        val response: T
    )

    data class NewsListResponseDto(
        val status: String,
        val total: Int,
        val startIndex: Int,
        val pageSize: Int,
        val currentPage: Int,
        val pages: Int,
        val orderBy: String,
        val results: List<NewsContentDto>
    )

    data class NewsContentDto (
        val id: String,
        val type: String,
        val sectionId: String,
        val sectionName: String,
        val webPublicationDate: String,
        val webTitle: String,
        val webUrl: String,
        val apiUrl: String,
        val fields: FieldsDto,
        val isHosted: Boolean,
        val pillarId: String,
        val pillarName: String
    ) {
        data class FieldsDto(
            val headline: String = "",
            val byline: String = "",
            val thumbnail: String = "",
            val body: String = ""
        )
    }

    data class NewsDetailResponseDto(
        val status: String,
        val content: NewsContentDto
    )
}

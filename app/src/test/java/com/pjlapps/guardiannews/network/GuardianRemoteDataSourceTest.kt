package com.pjlapps.guardiannews.network

import com.pjlapps.guardiannews.domain.NewsApiDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GuardianRemoteDataSourceTest {

    @MockK
    lateinit var apiService: GuardianApiService

    private lateinit var subject: GuardianRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        subject  = GuardianRemoteDataSource(
            apiService = apiService,
            guardianApiKey = "apiKey"
        )
    }

    @Test
    fun getNewsList() = runTest {
        val newsList: NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsListResponseDto> = mockk()
        coEvery {
            apiService.getNewsList(any(), any(), any(), any(), any())
        } returns newsList
        val result = subject.getNewsList(1, 1)
        assertThat(result, `is`(newsList))
        coVerify { apiService.getNewsList(any(), any(), any(), any(), any()) }
    }

    @Test
    fun getNewsItem() = runTest {
        val newsId = "newsId"
        val newsDetail: NewsApiDto.NewsApiResponseDto<NewsApiDto.NewsDetailResponseDto> = mockk()
        coEvery {
            apiService.getNewsDetail(any(), any(), any())
        } returns newsDetail
        val result = subject.getNewsItem(newsId)
        assertThat(result, `is`(newsDetail))
        coVerify { apiService.getNewsDetail(any(), any(), any()) }

    }
}
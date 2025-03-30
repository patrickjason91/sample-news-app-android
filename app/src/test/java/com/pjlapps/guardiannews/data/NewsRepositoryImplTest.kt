package com.pjlapps.guardiannews.data

import com.pjlapps.guardiannews.createNewsContentDto
import com.pjlapps.guardiannews.createNewsDetail
import com.pjlapps.guardiannews.createNewsListResponseDto
import com.pjlapps.guardiannews.domain.NewsApiDto
import com.pjlapps.guardiannews.network.RemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    @MockK
    lateinit var remoteDataSource: RemoteDataSource
    @MockK
    lateinit var appDatabase: AppDatabase
    @MockK
    lateinit var newsDao: NewsDao

    private lateinit var subject: NewsRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        subject = NewsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            appDatabase = appDatabase
        )
        every { appDatabase.newsDao() } returns newsDao
        every { newsDao.getNewsList(any(), any()) } returns listOf(createNewsDetail())
        every { newsDao.getNewsById(any()) } returns createNewsDetail()
        coEvery { remoteDataSource.getNewsList(any(), any()) } returns NewsApiDto.NewsApiResponseDto(
            response = createNewsListResponseDto()
        )
        coEvery { remoteDataSource.getNewsItem(any()) } returns NewsApiDto.NewsApiResponseDto(
            response = NewsApiDto.NewsDetailResponseDto(
                status = "ok",
                content = createNewsContentDto()
            )
        )
    }

    @Test
    fun getNewsList() = runTest {
        val newsList = subject.getNewsList()
        assertEquals(true, newsList.isSuccess)
        newsList.onSuccess {
            assertTrue(it.newsList.isNotEmpty())
        }
        coVerify { remoteDataSource.getNewsList(any(), any()) }
        verify(exactly = 0) { newsDao.getNewsList(any(), any()) }
        verify { newsDao.insertNewsList(any()) }
    }

    @Test
    fun `getNewsList from db`() = runTest {
        coEvery { remoteDataSource.getNewsList(any(), any()) } throws Exception()
        val newsList = subject.getNewsList()
        assertEquals(true, newsList.isSuccess)
        newsList.onSuccess {
            assertTrue(it.newsList.isNotEmpty())
        }
        coVerify { remoteDataSource.getNewsList(any(), any()) }
        verify { newsDao.getNewsList(any(), any()) }
    }

    @Test
    fun getNewsItem() = runTest {
        val newsId = "newsId"
        val newsDetail = subject.getNewsDetailsById(newsId)
        assertEquals(true, newsDetail.isSuccess)
        coVerify { remoteDataSource.getNewsItem(newsId) }
        verify(exactly = 0) { newsDao.getNewsById(any()) }
    }

    @Test
    fun `getNewsItem from db`() = runTest {
        coEvery { remoteDataSource.getNewsItem(any()) } throws Exception()
        val newsDetail = subject.getNewsDetailsById("newsId")
        verify { newsDao.getNewsById(any()) }
        assertEquals(true, newsDetail.isSuccess)
    }
}
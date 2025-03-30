package com.pjlapps.guardiannews

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pjlapps.guardiannews.data.EmptyResultException
import com.pjlapps.guardiannews.data.NewsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @MockK
    lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(newsRepository, StandardTestDispatcher())
    }

    @Test
    fun getNewsList() = runTest {
        coEvery { newsRepository.getNewsList() } returns Result.success(
            createNewsListResult()
        )
        viewModel.getNewsList()
        advanceUntilIdle()

        val newsListValue = viewModel.newsListState.value
        assertThat(newsListValue, `is`(notNullValue()))
        assertThat(newsListValue!!.newsList.size, `is`(1))

        coVerify{ newsRepository.getNewsList() }
        confirmVerified(newsRepository)
    }

    @Test
    fun `getNewsList error`() = runTest {
        coEvery { newsRepository.getNewsList() } returns Result.failure(
            EmptyResultException()
        )
        viewModel.getNewsList()
        advanceUntilIdle()

        val newsListErrorValue = viewModel.newsListErrorState.value
        assertThat(newsListErrorValue, `is`(true))
    }

    @Test
    fun getNewsDetails() = runTest{
        coEvery { newsRepository.getNewsDetailsById(any()) } returns Result.success(
            createNewsDetailResult()
        )
        viewModel.getNewsDetails("newsId")
        advanceUntilIdle()
        val newsDetailValue = viewModel.newsDetailState.value
        assertThat(newsDetailValue, `is`(notNullValue()))
    }

    @Test
    fun `getNewsDetail error`() = runTest {
        coEvery { newsRepository.getNewsDetailsById(any()) } returns Result.failure(
            SocketTimeoutException()
        )
        viewModel.getNewsDetails("newsId")
        advanceUntilIdle()
        val newsDetailErrorValue = viewModel.newsDetailErrorState.value
        assertThat(newsDetailErrorValue, `is`(true))
    }
}
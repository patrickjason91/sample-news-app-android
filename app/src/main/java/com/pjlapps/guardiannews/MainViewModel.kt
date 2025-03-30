package com.pjlapps.guardiannews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjlapps.guardiannews.data.NewsDetail
import com.pjlapps.guardiannews.data.NewsRepository
import com.pjlapps.guardiannews.domain.NewsListResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val newsRepository: NewsRepository
): ViewModel() {
    val newsListLiveData: MutableLiveData<NewsListResult> = MutableLiveData()
    val newsDetailLiveData: MutableLiveData<NewsDetail> = MutableLiveData()
    val newsListErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val newsDetailErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getNewsList(
        page: Int = 1,
        pageSize: Int = 20
    ) {
        viewModelScope.launch (Dispatchers.IO) {
            newsRepository.getNewsList(page, pageSize)
                .onSuccess { result ->
                    newsListLiveData.postValue(result)
                }
                .onFailure { failure ->
                    newsListErrorLiveData.postValue(true)
                }
        }
    }

    fun getNewsDetails(newsId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            val newsDetails = newsRepository.getNewsDetailsById(newsId)
            newsDetails.onSuccess { result ->
                newsDetailLiveData.postValue(result.newsDetail)
            }
            newsDetails.onFailure { failure ->
                newsDetailErrorLiveData.postValue(true)
            }
        }
    }
}
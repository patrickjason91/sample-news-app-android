package com.pjlapps.guardiannews.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pjlapps.guardiannews.MainViewModel
import com.pjlapps.guardiannews.data.NewsDetail

@Composable
fun NewsListScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    viewModel.getNewsList()
    val newsListState by viewModel.newsListLiveData.observeAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true
    ) {
        items(
            items = newsListState?.newsList ?: emptyList(),
            key = { item -> item.id }
        ) { item ->
            Log.i("NewsListScreen", "item composed: $item")
            NewsListItem(item) {
                Log.i("NewsListScreen", "onClick: $item")
                navController.navigate(NewsDetailsPage(item.id))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsListItem(
    newsDetail: NewsDetail,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = true) {
                onClick()
            }
            .height(80.dp)
    ) {
        GlideImage(
            model = newsDetail.thumbnail,
            contentDescription = newsDetail.headline,
            modifier = Modifier.width(120.dp).height(80.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = newsDetail.headline,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 8.dp),
            fontSize = TextUnit(12f, TextUnitType.Sp)
        )
    }

}
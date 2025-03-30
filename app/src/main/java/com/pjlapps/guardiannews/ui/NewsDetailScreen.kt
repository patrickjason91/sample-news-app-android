package com.pjlapps.guardiannews.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pjlapps.guardiannews.MainViewModel
import com.pjlapps.guardiannews.ui.theme.ColorBackgroundTransparent
import com.pjlapps.guardiannews.ui.theme.ColorHeadlineText

/**
 * Loads and displays the news details based from the `newsId`.
 * Shows the news headline, thumbnail and body.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    newsId: String
) {
    Log.i("NewsDetailScreen", "called")
    val newsDetail by remember {
        viewModel.getNewsDetails(newsId)
        viewModel.newsDetailState
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {
        newsDetail?.let { newsDetail ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                GlideImage(
                    model = newsDetail.thumbnail,
                    contentDescription = newsDetail.headline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.FillWidth
                )
                Box(
                   modifier = Modifier
                       .align(Alignment.BottomCenter)
                       .background(ColorBackgroundTransparent)
                ) {
                    Text(
                        text = newsDetail.headline,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        color = ColorHeadlineText
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = AnnotatedString.fromHtml(
                        htmlString = newsDetail.body,
                        linkStyles = TextLinkStyles(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                                fontStyle = FontStyle.Normal,
                                color = Color.Blue
                            )
                        )
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 8.dp)
                )
            }
        }
    }
}
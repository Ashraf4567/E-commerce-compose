package com.example.e_commerce_compose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.e_commerce_compose.R
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

val bannersList = listOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = {bannersList.size})
    HorizontalPager(state = pagerState , pageSize = PageSize.Fixed(400.dp)) { page ->
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            AsyncImage(
                model = bannersList[page],
                contentDescription = "banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .align(Alignment.Center),
            )
        }
    }
}
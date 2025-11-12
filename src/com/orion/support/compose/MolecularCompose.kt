package com.orion.support.compose

import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import kotlin.math.absoluteValue
import androidx.compose.foundation.ExperimentalFoundationApi
import com.orion.support.compose.section.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MolecularCarousel() {
    val context = LocalContext.current
    val accentColor = Color(
        ContextCompat.getColor(context, android.R.color.system_accent1_300)
    )
    val pageCount = 3
    val pagerState = rememberPagerState(
        initialPage = 1, 
        pageCount = { pageCount } 
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .height(320.dp),
            contentPadding = PaddingValues(horizontal = 105.dp),
            pageSpacing = 0.dp
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                        val scale = lerp(
                            start = 0.6f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = scale
                        scaleY = scale

                        alpha = lerp(
                            start = 0.3f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .width(200.dp)
                    .padding(1.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                                if (pagerState.currentPage == page)
                                    accentColor
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = pagerState.currentPage == page,
                        enter = fadeIn(animationSpec = tween(durationMillis = 400)),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300))
                    ) {
                        when (page) {
                            0 -> AboutSection()
                            1 -> QuickSettingsSection()
                            2 -> LockscreenSection()
                        }
                    }
                }
            }
        }

        val current = pagerState.currentPage
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val selected = index == current
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(8.dp)
                        .width(if (selected) 22.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.3f
                            )
                        )
                )
            }
        }
    }
}

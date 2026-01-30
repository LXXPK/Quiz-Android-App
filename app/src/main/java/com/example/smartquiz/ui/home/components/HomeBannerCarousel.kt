
package com.example.smartquiz.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun HomeBannerCarousel(
    banners: List<Int>,
    modifier: Modifier = Modifier
) {
    if (banners.isEmpty()) return

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.85f
    val sideSpacer = (screenWidth - cardWidth) / 2


    LaunchedEffect(Unit) {
        listState.scrollToItem(1)
    }

    val centerIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val viewportCenter =
                (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2

            layoutInfo.visibleItemsInfo
                .filter { it.index in 1..banners.size }
                .minByOrNull { item ->
                    abs((item.offset + item.size / 2) - viewportCenter)
                }
                ?.index
                ?.minus(1)
                ?: 0
        }
    }

    LaunchedEffect(banners.size) {
        if (banners.size <= 1) return@LaunchedEffect

        while (true) {
            delay(3_000)
            val next = if (centerIndex == banners.lastIndex) 0 else centerIndex + 1
            coroutineScope.launch {
                listState.animateScrollToItem(next + 1)
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            item {
                Spacer(modifier = Modifier.width(sideSpacer))
            }

            items(banners.size) { index ->
                val isCenter = index == centerIndex

                Card(
                    modifier = Modifier
                        .width(cardWidth)
                        .height(170.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (isCenter) 10.dp else 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Image(
                        painter = painterResource(id = banners[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }


            item {
                Spacer(modifier = Modifier.width(sideSpacer))
            }
        }


        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            banners.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .height(4.dp)
                        .width(if (index == centerIndex) 28.dp else 16.dp)
                        .background(
                            if (index == centerIndex)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

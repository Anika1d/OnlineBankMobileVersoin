package com.fefuproject.druzhbank.extensions

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.flow.collect
import java.util.*

fun Modifier.roundedPlaceholder(visible: Boolean) = composed {
    placeholder(
        color = Color(0xCCE2E2E2),
        shape = CircleShape,
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}

val defaultDataFormatter = SimpleDateFormat("dd MMM HH:mm:ss", Locale.forLanguageTag("RU"))

@ExperimentalWearMaterialApi
@Composable
fun DefaultScaffold(
    scalingLazyListState: ScalingLazyListState? = null,
    verificationStateObserver: AuthStateObserver? = null,
    content: @Composable () -> Unit
) {
    MaterialTheme {
        Scaffold(
            timeText = {
                TimeText()
            },
            vignette = {
                if (scalingLazyListState != null) Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                scalingLazyListState?.let {
                    PositionIndicator(
                        scalingLazyListState = it
                    )
                }
            }
        ) {
            val isVerified = verificationStateObserver?.verificationState?.collectAsState()
            if (verificationStateObserver == null) {
                content()
            } else {
                if (isVerified!!.value == true) content()
                else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ожидание подключения к телефону...",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.size(25.dp))
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun ScalingLazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }
}

fun <T> createNullList(n: Int): List<T?> {
    val newList = mutableListOf<T?>()
    repeat(n) {
        newList.add(null)
    }
    return newList
}

fun <T> List<T?>.addNullList(n: Int): List<T?> {
    val newList = this.toMutableList()
    repeat(n) {
        newList.add(null)
    }
    return newList
}

// always merges from end
// OOBE-safe for the list being merged
fun <T> List<T>.mergeFromList(another: List<T>, n: Int): List<T> {
    val newList = this.toMutableList()
    var outCounter = 0
    repeat(n) {
        val i = it + 1
        if (another.size - i >= 0) newList[size - i] = another[another.size - i]
        else outCounter++
    }
    if (outCounter > 0) newList.dropLast(outCounter) // effectively merged nothing from the merged list this way
    return newList
}
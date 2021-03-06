package com.fefuproject.druzhbank.extensions

import androidx.compose.foundation.layout.*
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
                            text = "???????????????? ?????????????????????? ?? ????????????????...",
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
fun Modifier.roundedPlaceholder(visible: Boolean) = composed {
    placeholder(
        color = Color(0xCCE2E2E2),
        shape = CircleShape,
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}
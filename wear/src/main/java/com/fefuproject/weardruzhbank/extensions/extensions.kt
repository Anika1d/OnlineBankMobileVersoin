package com.fefuproject.weardruzhbank.extensions

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.composed
import androidx.wear.compose.material.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

fun Modifier.roundedPlaceholder(visible: Boolean) = composed {
    placeholder(
        color = Color(0xCCE2E2E2),
        shape = CircleShape,
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}

@ExperimentalWearMaterialApi
@Composable
fun DefaultScaffold(
    scalingLazyListState: ScalingLazyListState? = null,
    content: @Composable () -> Unit
) {
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
        content()
    }
}
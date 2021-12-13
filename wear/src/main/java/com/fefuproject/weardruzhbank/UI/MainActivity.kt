package com.fefuproject.weardruzhbank.UI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.fefuproject.weardruzhbank.R

data class MainActivityElement(
    val name: String,
    val icon: Int = R.drawable.ic_baseline_credit_card_24,
    // val Activity: Activity = R.drawable.ic_baseline_credit_card_24
)

val menuElements =
    listOf(MainActivityElement("Состояние счетов", R.drawable.ic_baseline_credit_card_24),
        MainActivityElement("Последние операции", R.drawable.ic_baseline_history_24),
        MainActivityElement("Быстрый перевод", R.drawable.ic_baseline_sync_alt_24),
        MainActivityElement("Настройки", R.drawable.ic_baseline_settings_24))


@ExperimentalWearMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }
}

@ExperimentalWearMaterialApi
@Composable
fun RootView() {
    val scalingLazyListState: ScalingLazyListState =
        rememberScalingLazyListState()
    Scaffold(
        timeText = {
            TimeText()
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = scalingLazyListState
            )
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 30.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 40.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            state = scalingLazyListState,
        ) {
            item {
                Text(
                    "Здравствуйте, <Пользователь>!",
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            items(menuElements) { index ->
                Chip(
                    modifier = Modifier
                        .width(140.dp)
                        .padding(top = 10.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = index.icon),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .wrapContentSize(align = Alignment.Center),
                        )
                    },
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.onPrimary,
                            text = index.name
                        )
                    },
                    onClick = {}
                )
            }
        }
    }
}
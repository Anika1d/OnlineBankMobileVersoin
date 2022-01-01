package com.fefuproject.weardruzhbank.UI.recent_ops

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.weardruzhbank.extensions.DefaultScaffold
import com.fefuproject.weardruzhbank.extensions.defaultDataFormatter
import com.fefuproject.weardruzhbank.extensions.roundedPlaceholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalWearMaterialApi
@AndroidEntryPoint
class RecentOpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scalingLazyListState: ScalingLazyListState =
                rememberScalingLazyListState()
            DefaultScaffold(scalingLazyListState = scalingLazyListState) {
                RootView(scalingLazyListState)
            }
        }
    }

    @Composable
    fun RootView(
        scalingLazyListState: ScalingLazyListState,
        viewModel: RecentOpViewModel = hiltViewModel()
    ) {
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() },
        ) {
            ScalingLazyColumn(
                state = scalingLazyListState,
                contentPadding = PaddingValues(
                    top = 30.dp,
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 40.dp
                ),
            ) {
                CardEvents(
                    this,
                    viewModel.cardEvents.value,
                    viewModel.isRefreshing.value,
                    defaultDataFormatter
                )
            }
        }
    }

    fun CardEvents(
        listScope: ScalingLazyListScope,
        events: List<HistoryInstrumentModel>?,
        isRefreshing: Boolean,
        dateFormat: SimpleDateFormat
    ) {
        listScope.items(events?.size ?: 3) { i ->
            Card(
                modifier = Modifier
                    .roundedPlaceholder(isRefreshing)
                    .fillMaxWidth(1f),
                onClick = {}) {
                if (events?.isNotEmpty() == true) {
                    Column {
                        Text(
                            text = "Перевод на карту",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        ) //TODO:TEMP
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = dateFormat.format(events[i].date),
                                style = TextStyle(fontWeight = FontWeight.Light)
                            )
                            Text(
                                text = events[i].count,
                                style = TextStyle(fontWeight = FontWeight.Light)
                            )
                        }
                    }
                }
            }
        }
    }
}
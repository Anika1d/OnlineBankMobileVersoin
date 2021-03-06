package com.fefuproject.druzhbank.UI.recent_ops

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.shared.account.domain.enums.PayType
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.druzhbank.extensions.DefaultScaffold
import com.fefuproject.druzhbank.extensions.OnBottomReached
import com.fefuproject.druzhbank.extensions.roundedPlaceholder
import libs.defaultDateTimeFormatter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

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
        val events by viewModel.cardEvents.collectAsState()
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
                autoCentering = false
            ) {
                CardEvents(
                    this,
                    events,
                    defaultDateTimeFormatter,
                    viewModel
                )
            }
        }
        scalingLazyListState.OnBottomReached {
            viewModel.loadNextPage()
        }
    }

    fun CardEvents(
        listScope: ScalingLazyListScope,
        events: List<HistoryInstrumentModel?>,
        dateFormat: SimpleDateFormat,
        viewModel: RecentOpViewModel
    ) {
        listScope.item {
            Text(
                "??????????????, ?????????? ?????????????????? ????????????????",
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        listScope.items(events) {
            val isLoading = it == null
            Card(
                modifier = Modifier
                    .roundedPlaceholder(isLoading)
                    .fillMaxWidth(1f),
                onClick = {
                    if (!isLoading) viewModel.repeatPayment(it!!)
                }) {
                if (!isLoading) {
                    val op = it!!
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = op.pay_type!!,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = if (op.destType == PayType.onCategory.ordinal) op.dest
                                else "*" + op.dest.takeLast(4),
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = dateFormat.format(op.date),
                                style = TextStyle(fontWeight = FontWeight.Light)
                            )
                            Text(
                                text = op.count + "??",
                                style = TextStyle(fontWeight = FontWeight.Light)
                            )
                        }
                    }
                }
            }
        }
    }
}
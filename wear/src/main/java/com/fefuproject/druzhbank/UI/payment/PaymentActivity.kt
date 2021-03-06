package com.fefuproject.druzhbank.UI.payment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.druzhbank.extensions.DefaultScaffold
import com.fefuproject.druzhbank.extensions.roundedPlaceholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalWearMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }

    @ExperimentalWearMaterialApi
    @Composable
    fun RootView(viewModel: PaymentActivityViewModel = hiltViewModel()) {
        val scalingLazyListState = rememberScalingLazyListState()
        val payments by viewModel.payments.collectAsState()
        DefaultScaffold(scalingLazyListState = scalingLazyListState) {
            if (payments.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Не удалось получить ни одного шаблона...",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Создайте несколько в приложении",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                val isRefreshing by viewModel.isRefreshing.collectAsState()
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    swipeEnabled = false,
                    onRefresh = {},
                ) {
                    ScalingLazyColumn(
                        state = scalingLazyListState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(
                            top = 30.dp,
                            bottom = 30.dp,
                            start = 50.dp,
                            end = 50.dp
                        ),
                        modifier = Modifier.fillMaxHeight(),
                        scalingParams = ScalingLazyColumnDefaults.scalingParams(
                            minTransitionArea = 1f,
                            maxTransitionArea = 1f
                        ),
                        autoCentering = false
                    ) {
                        items(payments) {
                            Button(
                                modifier = Modifier
                                    .size(120.dp)
                                    .roundedPlaceholder(it == null),
                                onClick = {
                                    if (it != null) viewModel.makePayment(
                                        it,
                                        applicationContext
                                    )
                                }) {
                                if (it != null) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = it.name, textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(7.dp))
                                        Text(
                                            text = it.sum.toString() + "р",
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
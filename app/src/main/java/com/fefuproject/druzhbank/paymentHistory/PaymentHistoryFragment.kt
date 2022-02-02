package com.fefuproject.druzhbank.paymentHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.fefuproject.druzhbank.DrawDefaultBackground
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.libs.OnBottomReached
import com.fefuproject.druzhbank.libs.roundedPlaceholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import libs.defaultDateTimeFormatter

@AndroidEntryPoint
class AllHistoryPaymentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PaymentHistoryMain()
            }
        }
    }
}

@Composable
fun PaymentHistoryMain(viewModel: PaymentHistoryViewModel = hiltViewModel()) {
    val listState = rememberLazyListState()
    var searchText by remember { mutableStateOf("") }
    val events by viewModel.cardEvents.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberSwipeRefreshState(isRefreshing)
    Scaffold { paddingValues ->
        DrawDefaultBackground()
        SwipeRefresh(state = refreshState, onRefresh = { viewModel.refresh() }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(0.dp, 7.dp)
                        .height(50.dp),
                    value = searchText,
                    onValueChange = { searchText = it },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "search icon"
                        )
                    },
                    singleLine = true
                )
                LazyColumn(state = listState, contentPadding = paddingValues) {
                    items(events) {
                        Card(
                            modifier = Modifier
                                .padding(7.dp, 7.dp)
                                .height(40.dp)
                                .fillMaxWidth()
                                .roundedPlaceholder(it == null),
                            elevation = 5.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = it?.pay_type ?: "Перевод на карту",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(10.dp, 0.dp)
                                        .fillMaxWidth(),
                                )
                                Text(
                                    text = if (it != null) defaultDateTimeFormatter.format(it.date) else "10.10.2021",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(20.dp,0.dp),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = if (it != null) it.count + " руб." else "100,00 руб.",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp, 0.dp),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    listState.OnBottomReached {
        viewModel.loadNextPage()
    }
}
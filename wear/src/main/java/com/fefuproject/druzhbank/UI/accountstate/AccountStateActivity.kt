package com.fefuproject.druzhbank.UI.accountstate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.shared.account.domain.models.CardModel
import com.fefuproject.shared.account.domain.models.HistoryInstrumentModel
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.extensions.DefaultScaffold
import com.fefuproject.druzhbank.extensions.OnBottomReached
import com.fefuproject.druzhbank.extensions.roundedPlaceholder
import libs.defaultDateTimeFormatter
import com.fefuproject.shared.account.domain.enums.PayType
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@ExperimentalWearMaterialApi
@AndroidEntryPoint
class AccountStateActivity : ComponentActivity() {

    @Inject
    lateinit var authObserver: AuthStateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scalingLazyListState: ScalingLazyListState =
                rememberScalingLazyListState()
            DefaultScaffold(scalingLazyListState, authObserver) {
                RootView(scalingLazyListState)
            }
        }
    }

    @Composable
    fun RootView(
        scalingLazyListState: ScalingLazyListState,
        viewModel: AccountStateViewModel = hiltViewModel()
    ) {
        val coroutineScope = rememberCoroutineScope()
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        val cardInfo by viewModel.cardInfo.collectAsState()
        val selectedCard by viewModel.selectedCard.collectAsState()
        val cardEvents by viewModel.cardEvents.collectAsState()
        val cardBeingBlocked by viewModel.cardBeingBlocked.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh() },
        ) {
            if (cardInfo == null || (cardInfo != null && cardInfo!!.isNotEmpty())) {
                ScalingLazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 40.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    state = scalingLazyListState,
                    autoCentering = false
                ) {
                    item {
                        Spacer(modifier = Modifier.size(100.dp))
                    }
                    CardDetails(
                        this,
                        isRefreshing,
                        if (cardInfo?.isNotEmpty() == true) cardInfo?.get(selectedCard) else null,
                        cardBeingBlocked,
                        viewModel,
                        coroutineScope
                    )
                    CardEvents(this, cardEvents, defaultDateTimeFormatter)
                }
                CardRow(selectedCard, cardInfo, viewModel, scalingLazyListState)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "К сожалению, у вас нет ни одного финансового инструмента для просмотра...",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Создайте его в приложении!",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        scalingLazyListState.OnBottomReached {
            if(!isRefreshing)
                viewModel.loadNextHistoryPage()
        }
    }

    fun CardDetails(
        listScope: ScalingLazyListScope,
        isRefreshing: Boolean,
        selectedCard: CardModel?,
        cardBeingBlocked: CardModel?,
        viewModel: AccountStateViewModel,
        coroutineScope: CoroutineScope,
    ) {
        listScope.item {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Баланс", style = TextStyle(fontWeight = FontWeight.Light))
                    Text(
                        text = if (selectedCard != null) selectedCard.count + 'р' else "0000000",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.roundedPlaceholder(isRefreshing)
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                if (cardBeingBlocked != null) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(ButtonDefaults.LargeButtonSize)
                    )
                } else {
                    Button(
                        modifier = Modifier
                            .size(ButtonDefaults.LargeButtonSize)
                            .roundedPlaceholder(isRefreshing),
                        onClick = {
                            coroutineScope.launch {
                                viewModel.blockCard()
                                Toast.makeText(
                                    applicationContext,
                                    "Карта успешно заблокирована",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                            contentDescription = "Card lock"
                        )
                    }
                }
            }
        }
    }

    private fun CardEvents(
        listScope: ScalingLazyListScope,
        events: List<HistoryInstrumentModel?>,
        dateFormat: SimpleDateFormat
    ) {
        listScope.items(events) { event ->
            Card(
                modifier = Modifier
                    .roundedPlaceholder(event == null)
                    .fillMaxWidth(1f),
                onClick = {}) {
                if (event == null) return@Card
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = event.pay_type!!,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (event.destType == PayType.onCategory.ordinal) event.dest
                            else "*" + event.dest.takeLast(4),
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = dateFormat.format(event.date),
                            style = TextStyle(fontWeight = FontWeight.Light)
                        )
                        Text(
                            text = event.count + 'р',
                            style = TextStyle(fontWeight = FontWeight.Light)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun CardRow(
        selectedCard: Int,
        cardInfo: List<CardModel>?,
        viewModel: AccountStateViewModel,
        scalingLazyListState: ScalingLazyListState
    ) {
        val shouldShrink =
            if (scalingLazyListState.layoutInfo.visibleItemsInfo.isNotEmpty())
                if (scalingLazyListState.layoutInfo.visibleItemsInfo[0].index == 0)
                    scalingLazyListState.layoutInfo.visibleItemsInfo[0].unadjustedOffset < -110 else true else false
        val rowYOffset by animateDpAsState(
            targetValue = if (shouldShrink) 20.dp else 40.dp
        )
        Row(
            modifier = Modifier
                .offset(0.dp, rowYOffset)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(cardInfo?.size ?: 3) {
                val transition = updateTransition(
                    targetState = it,
                    label = "card transition"
                )
                val size by transition.animateDp(label = "size") { state ->
                    val temp = if (state == selectedCard) 70.dp else 40.dp
                    if (shouldShrink) temp * 0.4f else temp
                }
                val textSize by transition.animateInt(label = "textSize") { state ->
                    if (shouldShrink) 0 else if (state == selectedCard) 14 else 10
                }
                val textOffset by transition.animateInt(label = "textOffset") { state ->
                    if (state == selectedCard) 8 else 5
                }
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier
                            .offset(-1.dp, textOffset.dp)
                            .roundedPlaceholder(cardInfo == null),
                        text = if (cardInfo != null) cardInfo[it].number.takeLast(4) else "1234",
                        textAlign = TextAlign.Center,
                        fontSize = textSize.sp
                    )
                    Image(
                        modifier = Modifier
                            .size(size)
                            .clickable {
                                if (cardInfo != null)
                                    viewModel.changeCard(it)
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_credit_card_24),
                        contentDescription = "card image"
                    )
                }
            }
        }
    }
}
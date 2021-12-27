package com.fefuproject.weardruzhbank.UI.accountstate

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.fefuproject.weardruzhbank.Model.AuthStateObserver
import com.fefuproject.weardruzhbank.R
import com.fefuproject.weardruzhbank.extensions.DefaultScaffold
import com.fefuproject.weardruzhbank.extensions.roundedPlaceholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
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
            DefaultScaffold(scalingLazyListState) {
                RootView(scalingLazyListState)
            }
        }
    }

    @Composable
    fun RootView(scalingLazyListState: ScalingLazyListState) {
        val verified = remember { authObserver.verificationState }
        if (verified.value) AccountView(scalingLazyListState)
    }

    @Composable
    fun AccountView(
        scalingLazyListState: ScalingLazyListState,
        viewModel: AccountStateViewModel = hiltViewModel()
    ) {
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        val cardInfo = viewModel.cardInfo.collectAsState()
        val selectedCard = remember { mutableStateOf(0) }
        val cardEvents = viewModel.cardEvents.collectAsState()
        //scalingLazyListState.layoutInfo.
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh(card = selectedCard.value) },
        ) {
            if (cardInfo.value == null || (cardInfo.value != null && cardInfo.value!!.isNotEmpty())) {
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
                ) {
                    item {
                        Spacer(modifier = Modifier.size(100.dp))
                    }
                    CardDetails(
                        this,
                        isRefreshing,
                        if (cardInfo.value?.isNotEmpty() == true) cardInfo.value?.get(selectedCard.value) else null,
                    )
                    CardEvents(this, cardEvents.value, isRefreshing, viewModel.dataFormatter)
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
    }

    fun CardDetails(
        listScope: ScalingLazyListScope,
        isRefreshing: Boolean,
        selectedCard: CardModel?
    ) {
        //listScope.item { Spacer(modifier = Modifier.size(0.dp, 20.dp)) }
        listScope.item {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Баланс", style = TextStyle(fontWeight = FontWeight.Light))
                    Text(
                        text = if (selectedCard != null) selectedCard.count else "0000000",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.roundedPlaceholder(isRefreshing)
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    modifier = Modifier
                        .size(ButtonDefaults.LargeButtonSize)
                        .roundedPlaceholder(isRefreshing),
                    onClick = {},
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                        contentDescription = "Card lock"
                    )
                }
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

    @Composable
    fun CardRow(
        selectedCard: MutableState<Int>,
        cardInfo: State<List<CardModel>?>,
        viewModel: AccountStateViewModel,
        scalingLazyListState: ScalingLazyListState
    ) {
        val shouldShrink =
            if (scalingLazyListState.layoutInfo.visibleItemsInfo.isNotEmpty())
                if (scalingLazyListState.layoutInfo.visibleItemsInfo[0].index == 0)
                    scalingLazyListState.layoutInfo.visibleItemsInfo[0].unadjustedOffset < -40 else true else false
        val rowYOffset by animateDpAsState(
            targetValue = if (shouldShrink) 20.dp else 40.dp
        )
        Row(
            modifier = Modifier
                .offset(0.dp, rowYOffset)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) {
                val transition = updateTransition(
                    targetState = it,
                    label = "card transition"
                )
                val size by transition.animateDp(label = "size") { state ->
                    val temp = if (state == selectedCard.value) 70.dp else 40.dp
                    if (shouldShrink) temp * 0.4f else temp
                }
                val textSize by transition.animateInt(label = "textSize") { state ->
                    if (shouldShrink) 0 else if (state == selectedCard.value) 14 else 10
                }
                val textOffset by transition.animateInt(label = "textOffset") { state ->
                    if (state == selectedCard.value) 8 else 5
                }
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier
                            .offset(-1.dp, textOffset.dp)
                            .roundedPlaceholder(cardInfo.value == null),
                        text = if (cardInfo.value != null) cardInfo.value!![it].number.drop(12) else "1234",
                        textAlign = TextAlign.Center,
                        fontSize = textSize.sp
                    )
                    Image(
                        modifier = Modifier
                            .size(size)
                            .clickable {
                                if (cardInfo.value != null) {
                                    selectedCard.value = it
                                    viewModel.refresh(it)
                                }
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_credit_card_24),
                        contentDescription = "card image"
                    )
                }
            }
        }
    }
}
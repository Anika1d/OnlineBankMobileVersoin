package com.fefuproject.weardruzhbank.UI.transfer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.weardruzhbank.di.AuthStateObserver
import com.fefuproject.weardruzhbank.R
import com.fefuproject.weardruzhbank.extensions.DefaultScaffold
import com.fefuproject.weardruzhbank.extensions.roundedPlaceholder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalWearMaterialApi
@AndroidEntryPoint
class TransferActivity : ComponentActivity() {
    @Inject
    lateinit var authObserver: AuthStateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultScaffold(verificationState = authObserver.verificationState) {
                RootView()
            }
        }
    }

    @Composable
    fun RootView(viewModel: TransferViewModel = hiltViewModel()) {
        val isLoading by viewModel.isLoading.collectAsState()
        val sourceToolListState = rememberScalingLazyListState()
        val targetToolListState = rememberScalingLazyListState()
        val selectionError =
            sourceToolListState.layoutInfo.centralItemIndex == targetToolListState.layoutInfo.centralItemIndex
                    || (sourceToolListState.layoutInfo.centralItemIndex == sourceToolListState.layoutInfo.totalItemsCount - 2)
                    || (targetToolListState.layoutInfo.centralItemIndex == targetToolListState.layoutInfo.totalItemsCount - 2)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Text("Выберите счета", style = TextStyle(fontWeight = FontWeight.Light))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ToolSelector(
                        listState = sourceToolListState,
                        isError = selectionError,
                        isLeft = true
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                        contentDescription = "arrow"
                    )
                    ReverseToolSelector(
                        listState = targetToolListState,
                        isError = selectionError,
                        isLeft = false
                    )
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        modifier = Modifier,
                        onClick = { viewModel.startTransaction() },
                        enabled = !selectionError
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_done_24),
                            contentDescription = "done"
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ToolSelector(
        listState: ScalingLazyListState,
        isError: Boolean,
        isLeft: Boolean,
        viewModel: TransferViewModel = hiltViewModel()
    ) {
        val cards by viewModel.sourceCards.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.width(100.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            items(cards?.size ?: 3) {
                Row(
                    horizontalArrangement = if (isLeft) Arrangement.End else Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true)
                        )
                    } else {
                        Text(
                            cards!!.get(it).instrumentType!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.layoutInfo.centralItemIndex - 1 == it)
                                    FontWeight.Normal else FontWeight.Light,
                                color = if (it == listState.layoutInfo.centralItemIndex - 1 && isError)
                                    Color.Red else Color.White,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true)
                        )
                    } else {
                        Text(
                            cards!!.get(it).name ?: "*" + cards!!.get(it).number!!.drop(12),
                            style = TextStyle(
                                fontWeight =
                                if (listState.layoutInfo.centralItemIndex - 1 == it)
                                    FontWeight.ExtraBold else FontWeight.Normal,
                                color = if (it == listState.layoutInfo.centralItemIndex - 1 && isError)
                                    Color.Red else Color.White
                            )
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }

    @Composable
    fun ReverseToolSelector( //THX GOOGLE FOR WORKING ZINDEX YEAH THAT'S SO GOOD THXXXXXXXXXX
        listState: ScalingLazyListState,
        isError: Boolean,
        isLeft: Boolean,
        viewModel: TransferViewModel = hiltViewModel()
    ) {
        val cards by viewModel.sourceCards.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier.width(100.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            items(cards?.size ?: 3) {
                Row(
                    horizontalArrangement = if (isLeft) Arrangement.End else Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true)
                        )
                    } else {
                        Text(
                            if (cards!!.get(it).name.isNullOrBlank()) "*" + cards!!.get(it).number!!.drop(
                                12
                            ) else cards!!.get(it).name!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.layoutInfo.centralItemIndex - 1 == it)
                                    FontWeight.ExtraBold else FontWeight.Normal,
                                color = if (it == listState.layoutInfo.centralItemIndex - 1 && isError)
                                    Color.Red else Color.White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    if (isLoading) {
                        Text(
                            "Тест",
                            modifier = Modifier
                                .roundedPlaceholder(true)
                        )
                    } else {
                        Text(
                            cards!!.get(it).instrumentType!!,
                            style = TextStyle(
                                fontWeight =
                                if (listState.layoutInfo.centralItemIndex - 1 == it)
                                    FontWeight.Normal else FontWeight.Light,
                                color = if (it == listState.layoutInfo.centralItemIndex - 1 && isError)
                                    Color.Red else Color.White,
                            )
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

package com.fefuproject.druzhbank.UI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.druzhbank.R
import com.fefuproject.druzhbank.UI.accountstate.AccountStateActivity
import com.fefuproject.druzhbank.UI.recent_ops.RecentOpActivity
import com.fefuproject.druzhbank.UI.transfer.TransferActivity
import com.fefuproject.druzhbank.extensions.DefaultScaffold
import com.fefuproject.druzhbank.extensions.roundedPlaceholder
import com.fefuproject.druzhbank.UI.main.MainActivityViewModel
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.google.android.gms.wearable.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

data class MainActivityElement(
    val name: String,
    val icon: Int,
    val Activity: Class<out Activity>
)

@ExperimentalFoundationApi
@ExperimentalWearMaterialApi
private val menuElements = listOf(
    MainActivityElement(
        "Состояние счетов",
        R.drawable.ic_baseline_credit_card_24,
        AccountStateActivity::class.java
    ),
    MainActivityElement(
        "Быстрый перевод",
        R.drawable.ic_baseline_sync_alt_24,
        TransferActivity::class.java
    ),
    MainActivityElement(
        "Быстрый платёж",
        R.drawable.ic_baseline_receipt_long_24,
        AccountStateActivity::class.java
    ),
    MainActivityElement(
        "Последние операции",
        R.drawable.ic_baseline_history_24,
        RecentOpActivity::class.java
    ),
)

@ExperimentalWearMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(), DataClient.OnDataChangedListener {
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var authObserver: AuthStateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginChecker()
        }
    }

    override fun onResume() {
        super.onResume()
        val dataClient = Wearable.getDataClient(this)
        if (!viewModel.tokenExists.value) viewModel.updateAuth(dataClient)
        dataClient.addListener(this)
    }

    override fun onPause() {
        super.onPause()
        Wearable.getDataClient(this).removeListener(this)
    }

    @Composable
    fun LoginChecker(viewModel: MainActivityViewModel = hiltViewModel()) {
        val tokenExists by viewModel.tokenExists.collectAsState()
        if (tokenExists) {
            RootView(viewModel)
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Кажется, вы ещё не осуществили вход в свой аккаунт...",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Войдите в моб. приложении",
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @ExperimentalWearMaterialApi
    @Composable
    fun RootView(viewModel: MainActivityViewModel = hiltViewModel()) {
        val scalingLazyListState: ScalingLazyListState =
            rememberScalingLazyListState()
        val username by viewModel.username.collectAsState()
        DefaultScaffold(scalingLazyListState, verificationStateObserver = authObserver) {
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
                    Row {
                        Text(
                            "Здравствуйте, ",
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                        Text(
                            modifier = Modifier.roundedPlaceholder(username == null),
                            text = username ?: "placeholder",
                            fontSize = 10.sp
                        )
                    }

                }
                items(menuElements) { item ->
                    Chip(
                        modifier = Modifier
                            .width(140.dp)
                            .padding(top = 10.dp),
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
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
                                text = item.name
                            )
                        },
                        onClick = {
                            startActivity(Intent(this@MainActivity, item.Activity))
                        }
                    )
                }
            }
        }
    }

    override fun onDataChanged(p0: DataEventBuffer) {
        viewModel.updateAuth(Wearable.getDataClient(this))
    }
}
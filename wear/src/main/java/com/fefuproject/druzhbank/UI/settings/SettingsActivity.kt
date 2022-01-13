package com.fefuproject.druzhbank.UI.settings

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.fefuproject.druzhbank.extensions.DefaultScaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalWearMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }

    @ExperimentalWearMaterialApi
    @Composable
    fun RootView(viewModel: SettingsActivityViewModel = hiltViewModel()) {
        DefaultScaffold {
            val pinEnabled by viewModel.pinEnabled.collectAsState()
            val currencyEnabled by viewModel.currencyEnabled.collectAsState()
            val isRefreshing by viewModel.isRefreshing.collectAsState()
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                swipeEnabled = false,
                onRefresh = {},
            ) {
                ScalingLazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(0.dp, 30.dp)
                ) {
                    item {
                        ToggleChip(
                            checked = pinEnabled,
                            onCheckedChange = {
                                viewModel.togglePINEnabled(
                                    applicationContext
                                )
                            },
                            label = {
                                Text(text = "Проверка\nPIN")
                            },
                            toggleIcon = {
                                ToggleChipDefaults.SwitchIcon(
                                    checked = pinEnabled
                                )
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .padding(top = 10.dp),
                        )
                    }
                    item {
                        ToggleChip(
                            checked = currencyEnabled,
                            onCheckedChange = {
                                viewModel.toggleCurrencyEnabled()
                            },
                            label = {
                                Text(text = "Курс\nВалют")
                            },
                            toggleIcon = {
                                ToggleChipDefaults.SwitchIcon(
                                    checked = currencyEnabled
                                )
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .padding(top = 10.dp),
                        )
                    }
                }
            }
        }
    }
}
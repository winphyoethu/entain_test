package com.winphyoethu.entain.racing

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.winphyoethu.entain.designsystem.basiccomponent.EntainBody
import com.winphyoethu.entain.designsystem.basiccomponent.EntainButton
import com.winphyoethu.entain.designsystem.basiccomponent.EntainOutlinedButton
import com.winphyoethu.entain.designsystem.basiccomponent.EntainSubTitle
import com.winphyoethu.entain.designsystem.entaincomponent.RaceCard
import com.winphyoethu.entain.designsystem.entaincomponent.RaceHeader
import com.winphyoethu.entain.designsystem.entaincomponent.RaceTypeSelect
import com.winphyoethu.entain.designsystem.ui.theme.EntainTheme
import com.winphyoethu.entain.designsystem.ui.theme.mediumDp
import com.winphyoethu.entain.designsystem.ui.theme.smallDp
import com.winphyoethu.entain.designsystem.ui.theme.xxLargeDp
import com.winphyoethu.entain.model.racing.RaceType
import com.winphyoethu.entain.model.racing.mockRaceInfoSection
import com.winphyoethu.entain.network.util.NetworkObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun RacingScreenRoute(viewModel: RacingViewModel = hiltViewModel()) {
    val llo = LocalLifecycleOwner.current
    val racingUiState by viewModel.racingStateFlow.collectAsStateWithLifecycle()
    val racingTypeUiState by viewModel.racingTypeStateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val networkObserver = remember { NetworkObserver(context) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            networkObserver.networkStatus.collectLatest { status ->
                if (status) {
                    // start the jobs when network connected
                    viewModel.startCountdown()
                    viewModel.fetchUpdate()
                } else {
                    // stop fetch job when network disconnected
                    viewModel.stopFetchUpdate()
                }
                val connectionMessage =
                    if (status) context.getString(R.string.connected) else context.getString(R.string.disconnected)
                Toast.makeText(context, connectionMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    DisposableEffect(llo) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // stop running jobs when the app goes foreground
                viewModel.startCountdown()
                viewModel.fetchUpdate()
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                // stop running jobs when the app goes background
                viewModel.stopCountdown()
                viewModel.stopFetchUpdate()
            }
        }
        llo.lifecycle.addObserver(observer)
        onDispose {
            llo.lifecycle.removeObserver(observer)
        }
    }

    RacingScreen(racingUiState, racingTypeUiState, viewModel::selectRaceType) {
        // stop running jobs and start new jobs
        viewModel.stopFetchUpdate()
        viewModel.startCountdown()
        viewModel.fetchUpdate()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RacingScreen(
    racingState: RacingUiState,
    racingTypeState: RacingTypeUiState,
    raceTypeClick: (raceType: RaceType) -> Unit,
    retryClick: () -> Unit
) {

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(mediumDp)
        ) {

            Image(
                painter = painterResource(com.winphyoethu.entain.designsystem.R.drawable.entain),
                modifier = Modifier
                    .size(100.dp, 30.dp)
                    .align(Alignment.CenterHorizontally),
                contentDescription = "Entain Logo"
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                racingTypeState.raceType.forEach {
                    RaceTypeSelect(it, onClick = raceTypeClick)
                    Spacer(modifier = Modifier.width(smallDp))
                }
            }

            when (racingState) {
                RacingUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is RacingUiState.InitialError -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            EntainSubTitle(
                                subtitle = stringResource(racingState.errorStringId)
                            )
                            EntainButton(text = stringResource(R.string.retry)) {
                                retryClick()
                            }
                        }
                    }
                }

                is RacingUiState.Show -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(smallDp)) {
                            racingState.raceInfoSection.forEach { section ->
                                stickyHeader(key = section.raceType.id) {
                                    RaceHeader(raceType = section.raceType)
                                }

                                items(
                                    items = section.raceInfoList,
                                    key = { it.raceId }) { item ->
                                    RaceCard(item)
                                }
                            }
                        }

                        racingState.errorStringId?.let {
                            Card(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = xxLargeDp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    EntainBody(
                                        body = stringResource(it),
                                        modifier = Modifier.padding(mediumDp)
                                    )
                                    EntainOutlinedButton(text = stringResource(R.string.retry)) {
                                        retryClick()
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

@Preview
@Composable
internal fun RacingScreenPreview() {
    EntainTheme(dynamicColor = false) {
        RacingScreen(
            RacingUiState.Show(
                listOf(mockRaceInfoSection),
                errorStringId = R.string.unable_to_fetch
            ),
            RacingTypeUiState(),
            raceTypeClick = {

            }, retryClick = {

            })
    }
}
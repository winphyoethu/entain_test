package com.winphyoethu.entain.racing

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.winphyoethu.entain.common.result.EntainResult
import com.winphyoethu.entain.data.racing.repository.CategoryId
import com.winphyoethu.entain.data.racing.repository.RacingRepository
import com.winphyoethu.entain.data.racing.util.RacingErrorCode
import com.winphyoethu.entain.model.racing.RaceType
import com.winphyoethu.entain.model.racing.mockRaceInfoSection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RacingViewModelTest {

    private val mockRacingRepository: RacingRepository = mock()
    private lateinit var viewmodel: RacingViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewmodel = RacingViewModel(mockRacingRepository, Dispatchers.Unconfined)
    }

    @Test
    fun `Fetch Update emit Show and map to correct horse race type`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Success(
                    listOf(
                        mockRaceInfoSection.copy(raceType = RaceType(CategoryId.HORSE_RACING.id))
                    )
                )
            )

            viewmodel.getRaceSection()

            assertEquals(
                RacingUiState.Show(
                    listOf(
                        mockRaceInfoSection.copy(
                            raceType = RaceType(
                                CategoryId.HORSE_RACING.id,
                                typeName = R.string.race_type_horse,
                                typeIconId = com.winphyoethu.entain.designsystem.R.drawable.horses
                            )
                        )
                    )
                ),
                viewmodel.racingStateFlow.value
            )
        }
    }

    @Test
    fun `Fetch Update emit Show and map to correct greyhound race type`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Success(
                    listOf(
                        mockRaceInfoSection.copy(raceType = RaceType(CategoryId.GREYHOUND_RACING.id))
                    )
                )
            )

            viewmodel.getRaceSection()

            assertEquals(
                RacingUiState.Show(
                    listOf(
                        mockRaceInfoSection.copy(
                            raceType = RaceType(
                                CategoryId.GREYHOUND_RACING.id,
                                typeName = R.string.race_type_greyhound,
                                typeIconId = com.winphyoethu.entain.designsystem.R.drawable.greyhound
                            )
                        )
                    )
                ),
                viewmodel.racingStateFlow.value
            )
        }
    }

    @Test
    fun `Fetch Update emit Show and map to correct harness race type`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Success(
                    listOf(
                        mockRaceInfoSection.copy(raceType = RaceType(CategoryId.HARNESS_RACING.id))
                    )
                )
            )

            viewmodel.getRaceSection()

            assertEquals(
                RacingUiState.Show(
                    listOf(
                        mockRaceInfoSection.copy(
                            raceType = RaceType(
                                CategoryId.HARNESS_RACING.id,
                                typeName = R.string.race_type_harness,
                                typeIconId = com.winphyoethu.entain.designsystem.R.drawable.harness
                            )
                        )
                    )
                ),
                viewmodel.racingStateFlow.value
            )
        }
    }

    @Test
    fun `Fetch Update emit HTTP Error`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Error(RacingErrorCode.HttpError)
            )

            viewmodel.getRaceSection()

            assertEquals(
                RacingUiState.InitialError(R.string.bad_request),
                viewmodel.racingStateFlow.value
            )
        }
    }

    @Test
    fun `Fetch Update emit Empty Racing Result Error`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Error(RacingErrorCode.EmptyRacingResultError)
            )

            viewmodel.getRaceSection()

            assertEquals(
                RacingUiState.InitialError(R.string.no_upcoming_races),
                viewmodel.racingStateFlow.value
            )
        }
    }

    @Test
    fun `Fetch Update emit Unexpected Error`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Error(RacingErrorCode.UnexpectedError)
            )

            viewmodel.getRaceSection()

            assertEquals(
                RacingUiState.InitialError(R.string.unable_to_fetch),
                viewmodel.racingStateFlow.value
            )
        }
    }

    @Test
    fun `Test countdown show LIVE`() {
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) { viewmodel.racingStateFlow.collect() }
            whenever(mockRacingRepository.getNextRacing(any())).thenReturn(
                EntainResult.Success(
                    listOf(
                        mockRaceInfoSection.copy(raceType = RaceType(CategoryId.HARNESS_RACING.id))
                    )
                )
            )

            viewmodel.getRaceSection()
            viewmodel.updateTimeToStart()

            assertEquals(
                "LIVE",
                (viewmodel.racingStateFlow.value as RacingUiState.Show).raceInfoSection[0].raceInfoList[0].timeToShow
            )
        }
    }

}
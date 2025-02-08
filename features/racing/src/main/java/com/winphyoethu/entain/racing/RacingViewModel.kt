package com.winphyoethu.entain.racing

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winphyoethu.entain.common.dispatcher.Dispatcher
import com.winphyoethu.entain.common.dispatcher.EntainDispatcher
import com.winphyoethu.entain.common.result.EntainResult
import com.winphyoethu.entain.common.util.secondsToTime
import com.winphyoethu.entain.data.racing.repository.CategoryId
import com.winphyoethu.entain.data.racing.repository.RacingRepository
import com.winphyoethu.entain.data.racing.util.RacingErrorCode
import com.winphyoethu.entain.model.racing.RaceInfoSection
import com.winphyoethu.entain.model.racing.RaceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

const val SCOPE_TIMEOUT = 5000L
const val COUNTDOWN = 1000L
const val REFRESH_UPDATE = 8000L

@HiltViewModel
class RacingViewModel @Inject constructor(
    val racingRepository: RacingRepository,
    @Dispatcher(EntainDispatcher.IO) val io: CoroutineDispatcher
) : ViewModel() {

    private val _racingStateFlow = MutableStateFlow<RacingUiState>(RacingUiState.Loading)
    val racingStateFlow = _racingStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SCOPE_TIMEOUT),
        initialValue = RacingUiState.Loading
    )

    private val _racingTypeStateFlow = MutableStateFlow(RacingTypeUiState())
    val racingTypeStateFlow = _racingTypeStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SCOPE_TIMEOUT),
        initialValue = RacingTypeUiState()
    )

    private var countdownJob: Job? = null
    private var listenJob: Job? = null

    private val selectedRaceType = mutableSetOf(
        CategoryId.HORSE_RACING.id,
        CategoryId.GREYHOUND_RACING.id,
        CategoryId.HARNESS_RACING.id
    )

    private val raceSection = mutableListOf<RaceInfoSection>()

    /**
     * function to start countdown
     */
    fun startCountdown() {
        if (countdownJob != null && countdownJob!!.isActive) {
            // check to avoid initializing job again
            return
        }
        countdownJob = viewModelScope.launch(io) {
            while (true) {
                updateTimeToStart()
                delay(COUNTDOWN)
            }
        }
    }

    fun updateTimeToStart() {
        _racingStateFlow.update { state ->
            if (state is RacingUiState.Show) {
                state.copy(
                    raceInfoSection = raceSection.filter {
                        selectedRaceType.size == 3 || selectedRaceType.contains(it.raceType.id)
                    }
                        .map { raceInfoSection ->
                            raceInfoSection.copy(
                                raceInfoList = raceInfoSection.raceInfoList.map { item ->
                                    val time =
                                        item.startTime - (Calendar.getInstance().timeInMillis / 1000)
                                    item.copy(timeToShow = time.secondsToTime())
                                }
                            )
                        }
                )
            } else {
                state
            }
        }
    }

    /**
     * function to fetch upcoming race
     */
    fun fetchUpdate() {
        if (listenJob != null && listenJob!!.isActive) {
            // check to avoid initializing job again
            return
        }
        if (raceSection.isEmpty()) {
            _racingStateFlow.update { RacingUiState.Loading }
        }
        listenJob = viewModelScope.launch {
            while (true) {
                getRaceSection()
                delay(REFRESH_UPDATE)
            }
        }
    }

    suspend fun getRaceSection() {
        val raceResult = racingRepository.getNextRacing()

        if (raceResult is EntainResult.Success) {
            raceSection.clear()
            raceSection.addAll(
                raceResult.data.map {
                    when (it.raceType.id) {
                        CategoryId.HORSE_RACING.id -> it.copy(raceType = horseRaceType)
                        CategoryId.GREYHOUND_RACING.id -> it.copy(raceType = greyhoundRaceType)
                        else -> it.copy(raceType = harnessRaceType)
                    }
                }.sortedBy { it.raceType.typeName }
            )
            _racingStateFlow.update {
                RacingUiState.Show(
                    if (selectedRaceType.size == 3) raceSection else raceSection.filter { section ->
                        selectedRaceType.contains(section.raceType.id)
                    }, errorStringId = null
                )
            }
        } else if (raceResult is EntainResult.Error) {
            val errorMessage = when (raceResult.e) {
                RacingErrorCode.HttpError -> R.string.bad_request
                RacingErrorCode.EmptyRacingResultError -> R.string.no_upcoming_races
                else -> R.string.unable_to_fetch
            }

            if (raceSection.isEmpty()) {
                // Show Try again if raceSection is empty for the first time
                stopCountdown()
                stopFetchUpdate()
                _racingStateFlow.update { RacingUiState.InitialError(errorMessage) }
            } else {
                // Show an error at the bottom if raceSection is not empty
                _racingStateFlow.update {
                    RacingUiState.Show(
                        if (selectedRaceType.size == 3) raceSection else raceSection.filter { section ->
                            selectedRaceType.contains(section.raceType.id)
                        }, errorStringId = errorMessage
                    )
                }
            }
        }
    }

    /**
     * Cancel running countdown job
     */
    fun stopCountdown() {
        countdownJob?.cancel()
    }

    /**
     * Cancel running fetch job
     */
    fun stopFetchUpdate() {
        listenJob?.cancel()
    }

    /**
     * function to select race type
     */
    fun selectRaceType(raceType: RaceType) {
        _racingTypeStateFlow.update {
            when {
                selectedRaceType.size == 1 && raceType.isSelected -> {
                    it.copy(raceType = it.raceType.map { item ->
                        selectedRaceType.add(item.id)
                        item.copy(isSelected = true)
                    }.toPersistentList())
                }

                else -> {
                    if (raceType.isSelected) {
                        selectedRaceType.remove(raceType.id)
                    } else {
                        selectedRaceType.add(raceType.id)
                    }
                    it.copy(raceType = it.raceType.map { item ->
                        if (item.id == raceType.id) {
                            item.copy(isSelected = !raceType.isSelected)
                        } else {
                            item
                        }
                    }.toPersistentList())
                }
            }
        }
    }

}

val horseRaceType = RaceType(
    id = CategoryId.HORSE_RACING.id,
    typeName = R.string.race_type_horse,
    typeIconId = com.winphyoethu.entain.designsystem.R.drawable.horses
)

val greyhoundRaceType = RaceType(
    id = CategoryId.GREYHOUND_RACING.id,
    typeName = R.string.race_type_greyhound,
    typeIconId = com.winphyoethu.entain.designsystem.R.drawable.greyhound
)

val harnessRaceType = RaceType(
    id = CategoryId.HARNESS_RACING.id,
    typeName = R.string.race_type_harness,
    typeIconId = com.winphyoethu.entain.designsystem.R.drawable.harness
)

/**
 * Ui state for Race Type
 */
@Stable
data class RacingTypeUiState(
    val raceType: PersistentList<RaceType> = persistentListOf(
        greyhoundRaceType, harnessRaceType, horseRaceType
    )
)

/**
 * Ui state for Racing
 */
@Stable
sealed class RacingUiState {

    data object Loading : RacingUiState()

    data class InitialError(val errorStringId: Int) : RacingUiState()

    data class Show(val raceInfoSection: List<RaceInfoSection>, val errorStringId: Int? = null) :
        RacingUiState()

}
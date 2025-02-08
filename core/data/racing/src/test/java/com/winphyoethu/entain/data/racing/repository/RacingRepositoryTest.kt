package com.winphyoethu.entain.data.racing.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import com.winphyoethu.entain.common.result.EntainResult
import com.winphyoethu.entain.common.util.secondsToTime
import com.winphyoethu.entain.data.racing.remote.datasource.RacingRemoteDatasource
import com.winphyoethu.entain.data.racing.remote.model.mockRaceInfo
import com.winphyoethu.entain.data.racing.remote.model.mockRaceInfoList
import com.winphyoethu.entain.data.racing.util.RacingErrorCode
import com.winphyoethu.entain.model.racing.RaceInfoSection
import com.winphyoethu.entain.model.racing.RaceType
import com.winphyoethu.entain.network.base.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.Calendar
import com.winphyoethu.entain.model.racing.RaceInfo as RaceInfo

class RacingRepositoryTest {

    private val racingRemoteDatasource: RacingRemoteDatasource = mock()
    private lateinit var racingRepository: RacingRepository

    @Before
    fun setUp() {
        racingRepository = RacingRepositoryImpl(racingRemoteDatasource, Dispatchers.Unconfined)
    }

    @Test
    fun `Get Racing by Category Id Return Correct Race Section`() {
        runTest {
            whenever(
                racingRemoteDatasource.getNextRacing(any())
            ).thenReturn(BaseModel(status = 200, data = mockRaceInfoList))

            val result = racingRepository.getNextRacing(1)

            Assert.assertEquals(
                EntainResult.Success(
                    listOf(
                        RaceInfoSection(
                            raceType = RaceType(id = "categoryId"), raceInfoList = listOf(
                                RaceInfo(
                                    raceId = mockRaceInfo.raceId,
                                    raceNumber = mockRaceInfo.raceNumber,
                                    meetingName = mockRaceInfo.meetingName,
                                    startTime = mockRaceInfo.advertisedStart.seconds,
                                    timeToShow = (mockRaceInfo.advertisedStart.seconds - (Calendar.getInstance().timeInMillis / 1000)).secondsToTime()
                                )
                            )
                        )
                    )
                ), result
            )
        }
    }

    @Test
    fun `Get Racing by Category Id Return EmptyRacingResultError`() {
        runTest {
            whenever(
                racingRemoteDatasource.getNextRacing(any())
            ).thenReturn(BaseModel(status = 200, data = null))

            val result = racingRepository.getNextRacing(1)

            Assert.assertEquals(
                EntainResult.Error<RaceInfo>(RacingErrorCode.EmptyRacingResultError),
                result
            )
        }
    }

    @Test
    fun `Get Racing by Category Id Return HttpError`() {
        runTest {
            whenever(
                racingRemoteDatasource.getNextRacing(any())
            ).thenReturn(BaseModel(status = 400, data = null))

            val result = racingRepository.getNextRacing(1)

            Assert.assertEquals(EntainResult.Error<RaceInfo>(RacingErrorCode.HttpError), result)
        }
    }

    @Test
    fun `Get Racing by Category Id Return UnexpectedError`() {
        runBlocking {
            whenever(
                racingRemoteDatasource.getNextRacing(any())
            ).doAnswer { throw Exception("Unexpected Error") }

            val result = racingRepository.getNextRacing(1)

            Assert.assertEquals(
                EntainResult.Error<RaceInfo>(RacingErrorCode.UnexpectedError),
                result
            )
        }
    }

}
package com.winphyoethu.entain.data.racing.repository

import com.winphyoethu.entain.common.dispatcher.Dispatcher
import com.winphyoethu.entain.common.dispatcher.EntainDispatcher
import com.winphyoethu.entain.common.result.EntainResult
import com.winphyoethu.entain.common.util.secondsToTime
import com.winphyoethu.entain.data.racing.remote.datasource.RacingRemoteDatasource
import com.winphyoethu.entain.data.racing.util.RacingErrorCode
import com.winphyoethu.entain.model.racing.RaceInfo
import com.winphyoethu.entain.model.racing.RaceInfoSection
import com.winphyoethu.entain.model.racing.RaceType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

/**
 * API for Racing Repository
 *
 * @see RacingRepositoryImpl for actual implementation
 */
interface RacingRepository {

    /**
     * get racing event by categoryId
     *
     * @param count (int) total number of racing event
     *
     * @return EntainResult [EntainResult] which type is list of RaceInfo[RaceInfo]
     */
    suspend fun getNextRacing(count: Int = 35): EntainResult<List<RaceInfoSection>>

}

/**
 * Implementation of RacingRepository [RacingRepository]
 *
 * @param remoteDatasource Remote Data Source of Racing [RacingRemoteDatasource]
 * @param io Coroutine IO Dispatcher
 */
internal class RacingRepositoryImpl @Inject constructor(
    val remoteDatasource: RacingRemoteDatasource,
    @Dispatcher(EntainDispatcher.IO) val io: CoroutineDispatcher
) : RacingRepository {

    override suspend fun getNextRacing(count: Int): EntainResult<List<RaceInfoSection>> {
        return withContext(io) {
            try {
                val response = remoteDatasource.getNextRacing(count = count)

                if (response.status == 200) {
                    if (response.data != null) {
                        val filteredMap =
                            response.data!!.raceSummaries.entries.groupBy { it.value.categoryId }
                                .mapValues { (_, items) ->
                                    items.sortedBy { it.value.advertisedStart.seconds }
                                        .take(5)
                                }

                        val raceInfoSectionList = mutableListOf<RaceInfoSection>()
                        filteredMap.forEach { (categoryId, item) ->
                            val raceInfoList = mutableListOf<RaceInfo>()
                            item.forEach { (_, value) ->
                                val time =
                                    (value.advertisedStart.seconds - (Calendar.getInstance().timeInMillis / 1000)).secondsToTime()
                                raceInfoList.add(
                                    RaceInfo(
                                        raceId = value.raceId,
                                        raceNumber = value.raceNumber,
                                        meetingName = value.meetingName,
                                        startTime = value.advertisedStart.seconds,
                                        timeToShow = time
                                    )
                                )
                            }
                            raceInfoSectionList.add(
                                RaceInfoSection(RaceType(id = categoryId), raceInfoList)
                            )
                        }
                        EntainResult.Success(raceInfoSectionList)
                    } else {
                        EntainResult.Error(RacingErrorCode.EmptyRacingResultError)
                    }
                } else {
                    EntainResult.Error(RacingErrorCode.HttpError)
                }
            } catch (e: Exception) {
                EntainResult.Error(RacingErrorCode.UnexpectedError)
            }
        }
    }

}

/**
 * ENUM class for Racing CategoryId
 */
enum class CategoryId(val id: String) {
    /**
     * HORSE RACING category id - 4a2788f8-e825-4d36-9894-efd4baf1cfae
     */
    HORSE_RACING("4a2788f8-e825-4d36-9894-efd4baf1cfae"),

    /**
     * GREYHOUND RACING category id - 9daef0d7-bf3c-4f50-921d-8e818c60fe61
     */
    GREYHOUND_RACING("9daef0d7-bf3c-4f50-921d-8e818c60fe61"),

    /**
     * HARNESS RACING category id - 161d9be2-e909-4326-8c2c-35ed71fb460b
     */
    HARNESS_RACING("161d9be2-e909-4326-8c2c-35ed71fb460b")
}
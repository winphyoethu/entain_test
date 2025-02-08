package com.winphyoethu.entain.data.racing.remote.datasource

import com.winphyoethu.entain.data.racing.remote.apiservice.RacingService
import com.winphyoethu.entain.data.racing.remote.model.RaceInfoList
import com.winphyoethu.entain.network.base.BaseModel
import javax.inject.Inject

/**
 * API for RacingRemoteDatasource
 *
 * @see RacingRemoteDatasourceImpl for actual implementation
 *
 */
internal interface RacingRemoteDatasource {

    /**
     * get racing information by category id
     *
     * @param count (int) total number of racing event
     *
     */
    suspend fun getNextRacing(count: Int): BaseModel<RaceInfoList>

}

/**
 * Implementation of RacingRemoteDatasource [RacingRemoteDatasource]
 *
 * @param racingService Retrofit Racing Api [RacingService]
 *
 */
internal class RacingRemoteDatasourceImpl @Inject constructor(val racingService: RacingService) :
    RacingRemoteDatasource {

    override suspend fun getNextRacing(
        count: Int
    ): BaseModel<RaceInfoList> {
        return racingService.getNextRacing(count = count)
    }

}
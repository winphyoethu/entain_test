package com.winphyoethu.entain.data.racing.remote.apiservice

import com.winphyoethu.entain.data.racing.remote.model.RaceInfoList
import com.winphyoethu.entain.network.base.BaseModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Racing Info Api Service
 */
internal interface RacingService {

    @GET("racing/")
    suspend fun getNextRacing(
        @Query("method") method: String = "nextraces",
        @Query("count") count: Int
    ): BaseModel<RaceInfoList>

}
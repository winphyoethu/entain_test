package com.winphyoethu.entain.data.racing.di

import com.winphyoethu.entain.data.racing.remote.apiservice.RacingService
import com.winphyoethu.entain.data.racing.remote.datasource.RacingRemoteDatasource
import com.winphyoethu.entain.data.racing.remote.datasource.RacingRemoteDatasourceImpl
import com.winphyoethu.entain.data.racing.repository.RacingRepository
import com.winphyoethu.entain.data.racing.repository.RacingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class RacingApiModule {

    @Provides
    internal fun providesRacingService(retrofit: Retrofit): RacingService {
        return retrofit.create(RacingService::class.java)
    }

}

@InstallIn(SingletonComponent::class)
@Module
abstract class RacingDatasourceModule {

    @Binds
    internal abstract fun bindRacingRemoteDatasource(remoteDatasource: RacingRemoteDatasourceImpl): RacingRemoteDatasource

}

@InstallIn(ViewModelComponent::class)
@Module
abstract class RacingRepositoryModule {

    @Binds
    internal abstract fun bindRacingRepository(racingRepositoryImpl: RacingRepositoryImpl): RacingRepository

}
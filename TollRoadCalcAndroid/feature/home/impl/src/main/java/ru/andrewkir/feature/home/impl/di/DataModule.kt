package ru.andrewkir.feature.home.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.andrewkir.feature.home.impl.data.api.MapApi
import ru.andrewkir.feature.home.impl.data.api.SearchApi
import ru.andrewkir.feature.home.impl.data.repository.MapRepository
import ru.andrewkir.feature.home.impl.data.repository.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMapApiService(retrofit: Retrofit): MapApi =
        retrofit.create(MapApi::class.java)

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)


    @Singleton
    @Provides
    fun provideMapRepository(
        api: MapApi,
    ): MapRepository =
        MapRepository(
            api = api,
        )

    @Provides
    @Singleton
    fun provideSearchRepository(
        api: SearchApi,
    ): SearchRepository =
        SearchRepository(api)
}
package ru.andrewkir.feature.home.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.andrewkir.feature.home.impl.data.api.MapApi
import ru.andrewkir.feature.home.impl.data.repository.MapRepository
import ru.andrewkir.feature.home.impl.data.repository.SearchRepository
import ru.andrewkir.feature.home.impl.domain.interactor.MapInteractor
import ru.andrewkir.feature.home.impl.domain.interactor.SearchInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideMapInteractor(
        repo: MapRepository,
    ): MapInteractor =
        MapInteractor(
            repository = repo,
        )

    @Singleton
    @Provides
    fun provideMSearchInteractor(
        repo: SearchRepository,
    ): SearchInteractor =
        SearchInteractor(
            repository = repo,
        )
}
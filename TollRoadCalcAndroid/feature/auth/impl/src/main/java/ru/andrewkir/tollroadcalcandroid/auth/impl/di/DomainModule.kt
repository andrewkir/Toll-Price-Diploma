package ru.andrewkir.tollroadcalcandroid.auth.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.andrewkir.tollroadcalcandroid.auth.api.interactor.AuthInteractor
import ru.andrewkir.tollroadcalcandroid.auth.impl.data.repository.AuthRepository
import ru.andrewkir.tollroadcalcandroid.auth.impl.domain.interactor.AuthInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideMapInteractor(
        repo: AuthRepository,
    ): AuthInteractor =
        AuthInteractorImpl(
            repository = repo,
        )
}
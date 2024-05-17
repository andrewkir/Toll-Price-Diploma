package ru.andrewkir.core.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.andrewkir.core.data.repository.TokensRepository
import ru.andrewkir.core.domain.interactor.TokensInteractor
import ru.andrewkir.core.domain.interactor.TokensInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun provideTokensInteractor(
        repo: TokensRepository,
        sharedPreferences: SharedPreferences,
    ): TokensInteractor =
        TokensInteractorImpl(
            tokensRepository = repo,
            prefs = sharedPreferences,
        )
}
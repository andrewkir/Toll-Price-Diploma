package ru.andrewkir.tollroadcalcandroid.auth.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.andrewkir.core.domain.interactor.TokensInteractor
import ru.andrewkir.core.domain.interactor.TokensInteractorImpl
import ru.andrewkir.tollroadcalcandroid.auth.impl.data.api.AuthApi
import ru.andrewkir.tollroadcalcandroid.auth.impl.data.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthApi,
        tokensInteractor: TokensInteractor,
    ): AuthRepository =
        AuthRepository(
            api = api,
            tokensInteractor = tokensInteractor,
        )
}
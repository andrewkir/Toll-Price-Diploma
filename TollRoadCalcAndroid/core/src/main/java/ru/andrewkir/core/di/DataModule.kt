package ru.andrewkir.core.di

import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.andrewkir.core.data.api.TokensApi
import ru.andrewkir.core.data.repository.TokensRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideTokensApiService(retrofit: Retrofit): TokensApi =
        retrofit.create(TokensApi::class.java)

    @Singleton
    @Provides
    fun provideTokensRepository(
        api: Lazy<TokensApi>,
    ): TokensRepository =
        TokensRepository(
            api = api,
        )
}
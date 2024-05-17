package ru.andrewkir.tollroadcalcandroid.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.andrewkir.feature.onboarding.impl.domain.manager.LocalUserManager
import ru.andrewkir.feature.onboarding.impl.domain.usecases.AppEntryUseCases
import ru.andrewkir.feature.onboarding.impl.domain.usecases.ReadAppEntry
import ru.andrewkir.feature.onboarding.impl.domain.usecases.SaveAppEntry
import ru.andrewkir.tollroadcalcandroid.data.manager.LocalUserManagerImpl
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager,
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager),
    )

    @Provides
    @Singleton
    fun provideDGisContext(
        @ApplicationContext applicationContext: android.content.Context
    ): Context =
        DGis.initialize(applicationContext)
}
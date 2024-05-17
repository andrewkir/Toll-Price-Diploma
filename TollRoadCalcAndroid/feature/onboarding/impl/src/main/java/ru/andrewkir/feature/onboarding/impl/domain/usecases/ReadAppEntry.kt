package ru.andrewkir.feature.onboarding.impl.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.andrewkir.feature.onboarding.impl.domain.manager.LocalUserManager

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {

    operator fun invoke(): Flow<Boolean> =
        localUserManager.readAppEntry()
}
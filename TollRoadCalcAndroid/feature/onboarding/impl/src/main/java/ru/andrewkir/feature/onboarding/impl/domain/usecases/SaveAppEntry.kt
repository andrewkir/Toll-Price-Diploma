package ru.andrewkir.feature.onboarding.impl.domain.usecases

import ru.andrewkir.feature.onboarding.impl.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}
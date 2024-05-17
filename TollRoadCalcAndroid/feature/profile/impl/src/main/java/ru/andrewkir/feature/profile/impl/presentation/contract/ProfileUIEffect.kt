package ru.andrewkir.feature.profile.impl.presentation.contract

import ru.andrewkir.core.presentation.contract.UIEffect

sealed class ProfileUIEffect: UIEffect {

    object OpenAuthScreen: ProfileUIEffect()

    class ShowSnackbar(val message: String): ProfileUIEffect()
}
package ru.andrewkir.feature.profile.impl.presentation.contract

import ru.andrewkir.core.presentation.contract.UIState


data class ProfileUIState(
    val isUserAuthorized: Boolean = false,
    val username: String? = "",
    val email: String? = "",
): UIState {
}
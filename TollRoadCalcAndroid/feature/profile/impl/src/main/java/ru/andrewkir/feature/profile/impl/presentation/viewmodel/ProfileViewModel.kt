package ru.andrewkir.feature.profile.impl.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.andrewkir.core.domain.interactor.TokensInteractor
import ru.andrewkir.core.presentation.viewmodel.BaseViewModel
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIEffect
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIEvent
import ru.andrewkir.feature.profile.impl.presentation.contract.ProfileUIState
import ru.andrewkir.tollroadcalcandroid.auth.api.interactor.AuthInteractor
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val tokensInteractor: TokensInteractor,
) : BaseViewModel<ProfileUIEvent, ProfileUIState, ProfileUIEffect>(
    ProfileUIState()
) {

    init {
        if (!authInteractor.isUserAuthorized()) {
            setEffect(ProfileUIEffect.OpenAuthScreen)
        }

        setState {
            copy(
                username = tokensInteractor.username,
                email = tokensInteractor.email
            )
        }
    }

    override fun handleEvent(event: ProfileUIEvent) {
        when (event) {
            ProfileUIEvent.OnAuthClicked ->
                setEffect(ProfileUIEffect.OpenAuthScreen)

            ProfileUIEvent.OnScreenOpened ->
                setState {
                    copy(
                        isUserAuthorized = authInteractor.isUserAuthorized(),
                        username = tokensInteractor.username,
                        email = tokensInteractor.email
                    )
                }

            ProfileUIEvent.OnLogoutClicked -> {
                authInteractor.logout()
                setState { copy(isUserAuthorized = authInteractor.isUserAuthorized()) }
            }
        }
    }
}
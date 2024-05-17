package ru.andrewkir.core.data.repository

import dagger.Lazy
import kotlinx.coroutines.runBlocking
import ru.andrewkir.core.data.api.TokensApi
import ru.andrewkir.core.domain.models.AccessTokenModel
import ru.andrewkir.core.domain.models.NetworkResult
import ru.andrewkir.core.domain.models.RefreshTokenModel
import ru.andrewkir.core.domain.models.TokensModel
import ru.andrewkir.core.presentation.viewmodel.BaseViewModel.Companion.safeApiCall

class TokensRepository(
    val api: Lazy<TokensApi>
) {

    fun verifyToken(token: String?): Boolean {
        if (token == null) return false
        else return runBlocking {
            val result = safeApiCall {
                api.get().verifyToken(
                    AccessTokenModel(
                        token = token
                    )
                )
            }
            return@runBlocking when (result) {
                is NetworkResult.NetworkError -> false
                is NetworkResult.GenericError -> false
                is NetworkResult.Success -> {
                    result.value.code() != 401
                }
            }
        }
    }

    fun updateTokens(refreshToken: String?): NetworkResult<TokensModel?>? {
        if (refreshToken == null) return null
        return runBlocking {
            val result = safeApiCall {
                api.get().refreshToken(
                    RefreshTokenModel(refreshToken = refreshToken)
                )
            }
            return@runBlocking result
        }
    }
}
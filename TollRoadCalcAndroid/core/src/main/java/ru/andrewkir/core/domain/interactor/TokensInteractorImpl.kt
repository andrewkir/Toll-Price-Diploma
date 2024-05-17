package ru.andrewkir.core.domain.interactor

import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson
import ru.andrewkir.core.data.repository.TokensRepository
import ru.andrewkir.core.domain.models.JwtPayload
import ru.andrewkir.core.domain.models.NetworkResult

class TokensInteractorImpl(
    private val tokensRepository: TokensRepository,
    private val prefs: SharedPreferences
) : TokensInteractor {

    override var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) {
            prefs.edit().putString(ACCESS_TOKEN, value).apply()
        }

    override var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) {
            prefs.edit().putString(REFRESH_TOKEN, value).apply()
        }

    override val username: String
        get() {
            val token = accessToken
            if (token != null) {
                val secondPayload = token.split('.')[1]
                val str = Base64.decode(secondPayload, Base64.DEFAULT).toString(charset("UTF-8"))
                val jwtPayload = Gson().fromJson(str, JwtPayload::class.java)
                return jwtPayload.username
            }
            return "Логин"
        }

    override val email: String
        get() {
            val token = accessToken
            val payload = if (token != null) token.split('.')[1] else null
            if (payload != null) {
                val str = Base64.decode(payload, Base64.DEFAULT).toString(charset("UTF-8"))
                val jwtPayload = Gson().fromJson(str, JwtPayload::class.java)
                return jwtPayload.email
            }
            return "Email"
        }

    override fun logout() {
        prefs.edit().clear().apply()
    }

    override fun isTokenExpired(): Boolean =
        !tokensRepository.verifyToken(accessToken)

    override fun updateTokens() {
        val result = tokensRepository.updateTokens(refreshToken)
        if (result is NetworkResult.Success) {
            accessToken = result.value?.accessToken
            refreshToken = result.value?.refreshToken
        } else {
            when (result) {
                is NetworkResult.GenericError -> {
                    accessToken = null
                    refreshToken = null
                }

                NetworkResult.NetworkError -> {
                    //Ничего не делаем
                }

                else -> {
                    //Ничего не делаем
                }
            }
        }
    }

    companion object {

        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
}
package com.eql.repositories.backendUserData.authenticator

import android.util.Log
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.data.remote.dto.RefreshTokenRequest
import com.example.petshelter.data.remote.repository.authenticator.AuthApiService
import com.example.petshelter.data.remote.useCases.SetAuthorizationHeaderUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class RemoteServerBackendAuthenticator @Inject constructor(
    private var userDataRepository: UserDataRepository,
    private val api:AuthApiService,
    private var setAuthorizationHeaderUseCase: SetAuthorizationHeaderUseCase,
): Authenticator {

    companion object {
        private const val TAG = "Authenticator"
        const val REFRESH_TOKEN_RETRY_COUNT = 10
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, "Auth token refresh in work!")
        synchronized(this) {

            if (!setAuthorizationHeaderUseCase.isSame(response.request)) {
                return setAuthorizationHeaderUseCase(response.request)
            }

            repeat(REFRESH_TOKEN_RETRY_COUNT) {
                try {
                    var tokenRefreshResponse: retrofit2.Response<RefreshTokenData>? = null

                    runBlocking {
                        val refreshToken = userDataRepository.getLoggedUserTokens().refreshToken
                        tokenRefreshResponse = api.refreshToken(
                            RefreshTokenRequest(
                                refreshToken
                            )
                        )
                    }

                    return when (tokenRefreshResponse?.isSuccessful) {
                        true -> {
                            val refreshTokenData = tokenRefreshResponse!!.body()!!
                            userDataRepository.saveLoggedUserTokens(
                                RefreshTokenData(
                                    accessToken = refreshTokenData.accessToken,
                                    refreshToken = refreshTokenData.refreshToken
                                )
                            )
                            setAuthorizationHeaderUseCase(response.request)
                        }
                        else -> null
                    }

                } catch (exception: Exception) {
                    Log.e(TAG, "Error: ${exception.localizedMessage}")
                    return null
                }
            }
            return null
        }

    }
}

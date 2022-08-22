package com.example.petshelter.data.remote.useCases

import androidx.annotation.CheckResult
import com.example.petshelter.domain.repository.localRepository.UserDataRepository
import okhttp3.Request
import javax.inject.Inject

/**
 * Sets the authorization header
 * to the given request with the tokens from [UserDataRepository]
 * and returns a new request instance.
 */

class SetAuthorizationHeaderUseCase @Inject constructor(
    private var userDataRepository: UserDataRepository
) {

    companion object {
        private const val AUTHORIZATION_KEY = "Authorization"
        private const val authStringType = "Bearer %s"
    }

    @CheckResult
    operator fun invoke(request: Request): Request =
        request.newBuilder()
            .header(
                name = AUTHORIZATION_KEY,
                value = String.format(authStringType, userDataRepository.getLoggedUserTokens().accessToken)
            )
            .build()

    /**
     * Checks if the given request already contains the same authentication header.
     */
    fun isSame(request: Request): Boolean =
        invoke(request).header(AUTHORIZATION_KEY) == request.header(AUTHORIZATION_KEY)
}

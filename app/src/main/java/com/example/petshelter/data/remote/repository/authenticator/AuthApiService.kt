package com.example.petshelter.data.remote.repository.authenticator


import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.data.remote.dto.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/auth/refresh")
    suspend fun refreshToken(@Body body: RefreshTokenRequest): Response<RefreshTokenData>
}

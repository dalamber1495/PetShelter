package com.example.petshelter.domain.useCases

import com.example.petshelter.common.Resource
import com.example.petshelter.data.remote.dto.LoginData
import com.example.petshelter.data.remote.dto.RefreshTokenData
import com.example.petshelter.data.remote.dto.RegisterData
import com.example.petshelter.domain.repository.backendAnimals.AnnouncementsRepository
import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(
    private val repository: AnnouncementsRepository,
) {
    operator fun invoke(login: String, password: String): Flow<Resource<RefreshTokenData>> = flow {
        try {
            emit(Resource.Loading())
            val tokenData = repository.authUser(LoginData(login,password))
            emit(Resource.Success(tokenData))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
    operator fun invoke(name:String,password:String,email:String):Flow<Resource<RefreshTokenData>> = flow{
        try {
            emit(Resource.Loading())
            val tokenData = repository.registerUser(RegisterData(email,password,name))
            emit(Resource.Success(tokenData))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

}
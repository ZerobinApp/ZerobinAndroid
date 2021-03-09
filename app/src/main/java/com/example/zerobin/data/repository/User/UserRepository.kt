package com.example.zerobin.data.repository.User

import com.example.zerobin.data.source.remote.RetrofitObject
import com.example.zerobin.domain.DataResult
import com.example.zerobin.domain.entity.Review
import com.example.zerobin.domain.entity.User
import com.example.zerobin.domain.mapper.DataToEntityExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository {
    private val zerobinClient = RetrofitObject.provideZerobinApi()

    suspend fun getMypageUser(): Flow<DataResult<List<User>>?> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMypageUser()

            if (!response.isSuccess!!) {
                emit(DataResult.Error(Exception(response.message)))
            }

            emit(response.result?.let { DataResult.Success(it.map(DataToEntityExtension::UserDataToEntity)) })
        }

    }
}
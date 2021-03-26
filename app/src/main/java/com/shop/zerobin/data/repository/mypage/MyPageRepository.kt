package com.shop.zerobin.data.repository.mypage

import android.content.Context
import com.shop.zerobin.data.source.remote.RetrofitObject
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Review
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.User
import com.shop.zerobin.domain.mapper.DataToEntityExtension
import com.shop.zerobin.domain.mapper.DataToEntityExtension.map
import com.shop.zerobin.domain.mapper.EntityToDataExtension.nickNameChangeEntityToData
import com.shop.zerobin.domain.mapper.EntityToDataExtension.signInEntityToData
import com.shop.zerobin.domain.mapper.EntityToDataExtension.signUpEntityToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyPageRepository(val context: Context) {

    private val pref = context.getSharedPreferences(PREF_DEFAULT, Context.MODE_PRIVATE)

    private val zerobinClient
        get() = RetrofitObject.provideZerobinApi(getJWT())

    suspend fun getMyPageReview(): Flow<DataResult<List<Review>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageReview()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(response.result.review.map(DataToEntityExtension::reviewDataToEntity)))
        }
    }


    suspend fun getMyPageShop(): Flow<DataResult<List<Shop>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageShop()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(response.result.shop.map(DataToEntityExtension::myPageShopDataToEntity)))
        }
    }

    suspend fun getMyPageStamp(): Flow<DataResult<List<Review>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageStamp()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(response.result.review.map(DataToEntityExtension::reviewDataToEntity)))
        }
    }

    suspend fun getMyPageUser(): Flow<DataResult<User>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getMyPageUser()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(response.result.map()))
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        nickname: String,
    ): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.signUp(signUpEntityToData(email, password, nickname))

            if (response.isSuccess == false) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }
            val jwt: String = response.result?.jwt ?: return@flow
            saveJWT(jwt)
            emit(DataResult.Success(Unit))
        }
    }

    suspend fun signIn(email: String, password: String): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.signIn(signInEntityToData(email, password))

            if (response.isSuccess == false) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val jwt: String = response.result?.jwt ?: return@flow
            saveJWT(jwt)
            emit(DataResult.Success(Unit))
        }
    }

    suspend fun nickNameChange(nickname: String): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.nickNameChange(nickNameChangeEntityToData(nickname))

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(Unit))
        }
    }

    private fun getJWT() = pref.getString(PREF_JWT, "") ?: ""

    private fun saveJWT(jwt: String) {
        pref.edit()
            .putString(PREF_JWT, jwt)
            .apply()
    }

    companion object {
        const val PREF_DEFAULT = "PREF_DEFAULT"
        const val PREF_JWT = "PREF_JWT"
    }
}
package com.shop.zerobin.data.repository.shop

import android.content.Context
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.data.source.remote.RetrofitObject
import com.shop.zerobin.domain.DataResult
import com.shop.zerobin.domain.entity.Hashtag
import com.shop.zerobin.domain.entity.Shop
import com.shop.zerobin.domain.entity.ShopDetail
import com.shop.zerobin.domain.mapper.DataToEntityExtension.hashtagDataToEntity
import com.shop.zerobin.domain.mapper.DataToEntityExtension.hashtagListDataToEntity
import com.shop.zerobin.domain.mapper.DataToEntityExtension.map
import com.shop.zerobin.domain.mapper.DataToEntityExtension.shopDataToEntity
import com.shop.zerobin.domain.mapper.EntityToDataExtension.shopListEntityToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONException

class ShopRepository(val context: Context) {

    private val pref =
        context.getSharedPreferences(MyPageRepository.PREF_DEFAULT, Context.MODE_PRIVATE)


    private val zerobinClient
        get() = RetrofitObject.provideZerobinApi(getJWT())

    fun isLogin(): Boolean {
        return getJWT().isNotEmpty()
    }

    private fun getJWT() = pref.getString(MyPageRepository.PREF_JWT, "") ?: ""

    fun saveHashTagList(list: List<Int>) {
        val editor = pref.edit()
        val json = JSONArray()
        for (hashTag in list) {
            json.put(hashTag)
        }
        if (list.isNotEmpty()) {
            editor.putString(PREF_HASH_TAG_LIST, json.toString())
        } else {
            editor.putString(PREF_HASH_TAG_LIST, null)
        }
        editor.apply()
    }

    fun getSavedHashTagList(): List<Int> {
        val json = pref.getString(PREF_HASH_TAG_LIST, null)
        val hashTagList = mutableListOf<Int>()
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val url = a.optInt(i)
                    hashTagList.add(url)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return hashTagList
    }

    suspend fun getShopList(): Flow<DataResult<Pair<List<String>, List<Shop>>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getShopList(shopListEntityToData(getSavedHashTagList()))

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val hashTag = response.result.hashtag.map(::hashtagDataToEntity)
            val shopList = response.result.shop.map(::shopDataToEntity)
            emit(DataResult.Success(hashTag to shopList))
        }
    }

    suspend fun getShopDetail(shopIndex: Int): Flow<DataResult<ShopDetail>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getShopDetail(shopIndex)

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val result = response.result.map()
            emit(DataResult.Success(result))
        }
    }

    suspend fun getSearchShopList(name: String): Flow<DataResult<List<Shop>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.searchShop(name)

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val result = response.result.shop.map(::shopDataToEntity)
            emit(DataResult.Success(result))
        }
    }

    suspend fun getHashtag(): Flow<DataResult<List<Hashtag>>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.getHashtag()

            if (!response.isSuccess) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            val result = response.result.map(::hashtagListDataToEntity)
            emit(DataResult.Success(result))
        }
    }

    suspend fun zzimShop(shopIndex: Int): Flow<DataResult<Unit>> {
        return flow {
            emit(DataResult.Loading)

            val response = zerobinClient.zzimShop(shopIndex)

            if (response.isSuccess != true) {
                emit(DataResult.Error(Exception(response.message)))
                return@flow
            }

            emit(DataResult.Success(Unit))
        }
    }

    fun saveFirstEnter() {
        pref.edit()
            .putBoolean(PREF_IS_FIRST_ENTER, false)
            .apply()
    }

    fun isFirstEnter(): Boolean {
        return pref.getBoolean(PREF_IS_FIRST_ENTER, true)
    }

    companion object {
        const val PREF_HASH_TAG_LIST = "PREF_HASH_TAG_LIST"
        const val PREF_IS_FIRST_ENTER = "PREF_IS_FIRST_ENTER"
    }
}

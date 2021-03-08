package com.example.zerobin.data.source.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObject {
    private const val devUrl = "http://dev.zerobin.shop/"
    private const val testUrl = "http://test.zerobin.shop/"
    private const val realUrl = "https://zerobin.shop/"

    fun provideZerobinApi(): ZerobinApi = getRetrofitBuild().create(ZerobinApi::class.java)

    private fun getRetrofitBuild() = Retrofit.Builder()
        .baseUrl(devUrl)
        .client(getOkhttpClient())
        .addConverterFactory(getGsonConverterFactory())
        .build()

    private fun getGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setDateFormat("E, dd MMMM yyyy HH:mm:ss X")
            .create()
        return GsonConverterFactory.create(gson)
    }

    private fun getOkhttpClient() = OkHttpClient.Builder().apply {

        //TimeOut 시간을 지정합니다.
        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        writeTimeout(5, TimeUnit.SECONDS)

        // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
        addInterceptor(getLoggingInterceptor())
    }.build()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}

package com.fefuproject.druzhbank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .callTimeout(10L, TimeUnit.SECONDS)
                //todo
            //.addInterceptor(NetworkStateInterceptor(this))
            //.addInterceptor(CustomHeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://lightfire.duckdns.org/")
            .client(okHttpClient)
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
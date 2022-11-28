package com.sema.data.di

import com.google.gson.*
import com.sema.data.api.ApiService
import com.sema.data.repository.PhotosRepository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    factory { PhotosRepository(get()) }
}

val networkModule = module {
    single<HttpLoggingInterceptor> { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC) }

    single<OkHttpClient> {
        val loggingInterceptor: HttpLoggingInterceptor = get()

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(loggingInterceptor)
        return@single okHttpBuilder.build()
    }

    single<Retrofit> {
        val baseURL = "https://jsonplaceholder.typicode.com/"
        val okHttpClient: OkHttpClient = get()

        return@single Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        val retrofit: Retrofit = get()
        retrofit.create(ApiService::class.java)
    }
}
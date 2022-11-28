package com.sema.data.api

import com.sema.data.model.PhotoResponse
import retrofit2.http.*

interface ApiService {

    @GET("/photos")
    suspend fun getPhotos(): List<PhotoResponse>

    @GET("/photos/{id}")
    suspend fun getPhotos(@Path("id") id: Int): PhotoResponse
}

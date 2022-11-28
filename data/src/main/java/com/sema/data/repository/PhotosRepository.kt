package com.sema.data.repository

import com.sema.data.api.ApiService
import com.sema.data.model.PhotoResponse

class PhotosRepository constructor(
    private val apiService: ApiService,
) {
    suspend fun getPhotos(): List<PhotoResponse> = apiService.getPhotos()
    suspend fun getPhotosDetail(id: Int): PhotoResponse = apiService.getPhotos(id)
}
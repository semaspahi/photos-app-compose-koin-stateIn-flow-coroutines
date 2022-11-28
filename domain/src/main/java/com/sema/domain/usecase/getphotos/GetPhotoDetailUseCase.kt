package com.sema.domain.usecase.getphotos

import com.sema.data.model.PhotoResponse
import com.sema.data.repository.PhotosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class GetPhotoDetailUseCase constructor(
    private val repository: PhotosRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    fun getPhotoDetail(id: Int): Flow<PhotoResponse> = flow {
        emit(repository.getPhotosDetail(id))
    }.flowOn(defaultDispatcher)

}


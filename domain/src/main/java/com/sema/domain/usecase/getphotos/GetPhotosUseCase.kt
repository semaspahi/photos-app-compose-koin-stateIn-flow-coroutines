package com.sema.domain.usecase.getphotos

import com.sema.data.model.PhotoResponse
import com.sema.data.repository.PhotosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class GetPhotosUseCase constructor(
    private val repository: PhotosRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    private var photosList = listOf<PhotoResponse>()

    fun getAlbums():Flow<List<PhotoResponse>> = flow {
        photosList = repository.getPhotos()
        photosList.getAlbums().collect { emit(it) }
    }.flowOn(defaultDispatcher)

    fun getAlbum(albumId: Int) = flow {
        photosList.getAlbumPhotos(albumId).collect { emit(it) }
    }.flowOn(defaultDispatcher)

    private fun List<PhotoResponse>.getAlbumPhotos(albumId: Int): Flow<List<PhotoResponse>> {
        return flowOf(this).map { it.filter { photo -> photo.albumId == albumId } }
    }

    private fun List<PhotoResponse>.getAlbums(): Flow<List<PhotoResponse>> {
        return flowOf(this).map { it.distinctBy { photo -> photo.albumId } }
    }

}


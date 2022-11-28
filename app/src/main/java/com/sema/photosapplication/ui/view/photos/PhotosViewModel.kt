package com.sema.photosapplication.ui.view.photos

import com.sema.data.model.PhotoResponse
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sema.data.common.Result
import com.sema.data.common.asResult
import com.sema.domain.usecase.getphotos.GetPhotosUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PhotosViewModel constructor(
    private val photosUseCase: GetPhotosUseCase,
    stateHandle: SavedStateHandle,
) : ViewModel() {

    private val albumId = checkNotNull(stateHandle.get<String>("id")).toInt()
    val state: StateFlow<PhotosUiState> = photosUseCase.photosUiStateResult(albumId)
        .stateIn(
            scope = viewModelScope,
            initialValue = PhotosUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        getPhotos(albumId)
    }

    fun getPhotos(albumId: Int) = viewModelScope.launch {
        photosUseCase.getAlbum(albumId)
    }
}

private fun GetPhotosUseCase.photosUiStateResult(albumId: Int): Flow<PhotosUiState> {
    return getAlbum(albumId).asResult().map { albumsResult ->
        when (albumsResult) {
            is Result.Success -> PhotosUiState.Ready(photos = albumsResult.data)
            is Result.Loading -> PhotosUiState.Loading
            is Result.Error -> PhotosUiState.Error(error = albumsResult.exception?.message)
        }
    }
}

sealed interface PhotosUiState {
    data class Ready(val photos: List<PhotoResponse>?) : PhotosUiState
    object Loading : PhotosUiState
    class Error(val error: String? = null) : PhotosUiState
}
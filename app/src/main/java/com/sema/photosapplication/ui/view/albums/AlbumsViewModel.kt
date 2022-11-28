package com.sema.photosapplication.ui.view.albums

import com.sema.data.model.PhotoResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sema.domain.usecase.getphotos.GetPhotosUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.sema.data.common.Result
import com.sema.data.common.asResult

class AlbumsViewModel constructor(
    private val photosUseCase: GetPhotosUseCase,
) : ViewModel() {

    val state: StateFlow<AlbumsUiState> = photosUseCase.albumsUiStateResult()
        .stateIn(
            scope = viewModelScope,
            initialValue = AlbumsUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        getAlbums()
    }

    fun getAlbums() = viewModelScope.launch {
        photosUseCase.getAlbums()
    }
}

private fun GetPhotosUseCase.albumsUiStateResult(): Flow<AlbumsUiState> {
    return getAlbums().asResult().map { albumsResult ->
        when (albumsResult) {
            is Result.Success -> AlbumsUiState.Ready(albums = albumsResult.data)
            is Result.Loading -> AlbumsUiState.Loading
            is Result.Error -> AlbumsUiState.Error(error = albumsResult.exception?.message)
        }
    }
}

sealed interface AlbumsUiState {
    data class Ready(val albums: List<PhotoResponse>?) : AlbumsUiState
    object Loading : AlbumsUiState
    class Error(val error: String? = null) : AlbumsUiState
}

package com.sema.photosapplication.ui.view.photodetail

import com.sema.data.model.PhotoResponse
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sema.data.common.Result
import com.sema.data.common.asResult
import com.sema.domain.usecase.getphotos.GetPhotoDetailUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PhotoDetailViewModel constructor(
    private val useCase: GetPhotoDetailUseCase,
    stateHandle: SavedStateHandle,
) : ViewModel() {

    private val photoId = checkNotNull(stateHandle.get<String>("id")).toInt()
    val state: StateFlow<PhotoDetailUiState> = useCase.photoUiStateResult(photoId)
        .stateIn(
            scope = viewModelScope,
            initialValue = PhotoDetailUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        getPhoto(photoId)
    }

    fun getPhoto(id: Int) = viewModelScope.launch {
        useCase.getPhotoDetail(id)
    }
}

private fun GetPhotoDetailUseCase.photoUiStateResult(id: Int): Flow<PhotoDetailUiState> {
    return getPhotoDetail(id).asResult().map { photoResult ->
        when (photoResult) {
            is Result.Success -> PhotoDetailUiState.Ready(photo = photoResult.data)
            is Result.Loading -> PhotoDetailUiState.Loading
            is Result.Error -> PhotoDetailUiState.Error(error = photoResult.exception?.message)
        }
    }
}

sealed interface PhotoDetailUiState {
    data class Ready(val photo: PhotoResponse?) : PhotoDetailUiState
    object Loading : PhotoDetailUiState
    class Error(val error: String? = null) : PhotoDetailUiState
}
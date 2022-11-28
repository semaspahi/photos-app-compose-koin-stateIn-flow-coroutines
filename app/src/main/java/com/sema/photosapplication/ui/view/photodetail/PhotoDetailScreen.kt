package com.sema.photosapplication.ui.view.photodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sema.component.LoadingBar
import com.sema.component.ShowToast
import com.sema.component.TopBarWithBack
import com.sema.data.model.PhotoResponse
import com.sema.photosapplication.ui.theme.Typography
import com.sema.photosapplication.ui.view.photos.PhotosUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotoDetailScreen(
    photoDetailViewModel: PhotoDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val photoUiState by photoDetailViewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBarWithBack(onBackClick = onBackClick)
            PhotoDetailContent(state = photoUiState)
        }
    }
}

@Composable
fun PhotoDetailContent(state: PhotoDetailUiState) {
        when (state) {
            is PhotoDetailUiState.Loading -> LoadingBar()
            is PhotoDetailUiState.Ready -> state.photo?.let { PhotoDetailItem(it) }
            is PhotoDetailUiState.Error -> state.error?.let { ShowToast(it) }
        }
}

@Composable
fun PhotoDetailItem(item: PhotoResponse) {
    Text(
        text = item.title,
        style = Typography.h4,
        modifier = Modifier.padding(24.dp),
    )
    item.url.let {
        Image(
            painter = rememberAsyncImagePainter(it),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
    }
}

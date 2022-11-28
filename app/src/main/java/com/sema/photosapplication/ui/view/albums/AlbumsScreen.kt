package com.sema.photosapplication.ui.view.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sema.component.LoadingBar
import com.sema.component.ShowToast
import com.sema.data.model.PhotoResponse
import com.sema.photosapplication.R
import com.sema.photosapplication.ui.theme.Shapes
import com.sema.photosapplication.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun AlbumsScreen(
    albumsViewModel: AlbumsViewModel = koinViewModel(),
    onClick: (Int) -> Unit
) {
    val albumsUiState: AlbumsUiState by albumsViewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(
                text = stringResource(R.string.albums_screen_title),
                style = Typography.h4,
                modifier = Modifier.padding(24.dp),
            )
            AlbumsContent(state = albumsUiState, onAlbumClick = onClick)
        }
    }
}

@Composable
fun AlbumsContent(state: AlbumsUiState, onAlbumClick: (Int) -> Unit) {
    when (state) {
        is AlbumsUiState.Loading -> LoadingBar()
        is AlbumsUiState.Ready -> {
            state.albums?.let { BindAlbums(it, onAlbumClick = onAlbumClick) }
        }
        is AlbumsUiState.Error -> state.error?.let { ShowToast(it) }
    }
}

@Composable
fun AlbumImage(item: PhotoResponse) {
    item.thumbnailUrl.let {
        Image(
            painter = rememberAsyncImagePainter(it),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
    }
}

@Composable
fun GridItem(item: PhotoResponse, onClick: (PhotoResponse) -> Unit) {
    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp)
            .clickable { onClick.invoke(item) },
    ) {
        AlbumImage(item)
        Text(
            modifier = Modifier.padding(18.dp),
            text = "Album ${item.albumId}",
            fontWeight = FontWeight.Bold,
            style = Typography.h5,
            color = Color.White
        )
    }
}

@Composable
fun BindAlbums(list: List<PhotoResponse>, onAlbumClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 180.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
    ) {
        items(
            items = list,
            key = { it.albumId },
            itemContent = {
                GridItem(it, onClick = { clickedItem ->
                    onAlbumClick.invoke(clickedItem.albumId)
                })
            })
    }
}

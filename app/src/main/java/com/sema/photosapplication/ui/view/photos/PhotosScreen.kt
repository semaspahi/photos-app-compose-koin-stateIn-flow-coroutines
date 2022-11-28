package com.sema.photosapplication.ui.view.photos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sema.component.LoadingBar
import com.sema.component.ShowToast
import com.sema.component.TopBarWithBack
import com.sema.data.model.PhotoResponse
import com.sema.photosapplication.R
import com.sema.photosapplication.ui.theme.Shapes
import com.sema.photosapplication.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotosScreen(
    photosViewModel: PhotosViewModel = koinViewModel(),
    onClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val photosUiState: PhotosUiState by photosViewModel.state.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBarWithBack(onBackClick = onBackClick)
            Text(
                text = stringResource(R.string.photos_screen_title),
                style = Typography.h4,
                modifier = Modifier.padding(24.dp),
            )
            PhotosContent(state = photosUiState, onImageClick = onClick)
        }
    }
}

@Composable
fun PhotosContent(state: PhotosUiState, onImageClick: (Int) -> Unit) {
    when (state) {
        is PhotosUiState.Loading -> LoadingBar()
        is PhotosUiState.Ready -> state.photos?.let { BindList(it, onImageClick = onImageClick) }
        is PhotosUiState.Error -> state.error?.let { ShowToast(it) }
    }
}

@Composable
fun PhotoImage(item: PhotoResponse) {
    item.url.let {
        Image(
            painter = rememberAsyncImagePainter(it),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
    }
}

@Composable
fun ListItem(item: PhotoResponse, onClick: (PhotoResponse) -> Unit) {
    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable { onClick.invoke(item) },
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
        ) {

            PhotoImage(item)
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(8.dp)
            ) {

                Text(
                    text = item.title,
                    style = Typography.subtitle1,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.url,
                    style = Typography.caption,
                    maxLines = 2
                )

            }
        }
    }
}

@Composable
fun BindList(list: List<PhotoResponse>, onImageClick: (Int) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)) {
        items(
            items = list,
            itemContent = {
                ListItem(it, onClick = { clickedItem ->
                    onImageClick.invoke(clickedItem.id)
                })
            })
    }
}

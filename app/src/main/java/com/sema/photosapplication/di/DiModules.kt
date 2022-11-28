package com.sema.photosapplication.di

import com.sema.domain.usecase.getphotos.GetPhotoDetailUseCase
import com.sema.domain.usecase.getphotos.GetPhotosUseCase
import com.sema.photosapplication.ui.view.albums.AlbumsViewModel
import com.sema.photosapplication.ui.view.photodetail.PhotoDetailViewModel
import com.sema.photosapplication.ui.view.photos.PhotosViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


val coroutinesModule = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
}

val viewModelModule = module {
    single { GetPhotosUseCase(get(), get(named("IODispatcher"))) }
    single { GetPhotoDetailUseCase(get(), get(named("IODispatcher"))) }
    viewModelOf(::AlbumsViewModel)
    viewModelOf(::PhotosViewModel)
    viewModelOf(::PhotoDetailViewModel)
}

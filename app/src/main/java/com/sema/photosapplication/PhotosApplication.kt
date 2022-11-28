package com.sema.photosapplication

import android.app.Application
import com.sema.data.di.networkModule
import com.sema.data.di.repositoryModule
import com.sema.photosapplication.di.coroutinesModule
import com.sema.photosapplication.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

class PhotosApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(coroutinesModule, networkModule, viewModelModule, repositoryModule)
        }
    }
}
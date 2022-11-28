# Photos App

With Photos App you are able to see albums, list the photos on the album and see the detail of the photo.
App is written fully with Jetpack compose.
UI Layer is exposed with StateFlow and StateIn.
Data Layer contains Repositories and Domain layer contains UseCase for grouping photos.

* UI
    * [Compose](https://developer.android.com/jetpack/compose)
    * [Material design](https://material.io/design)
    * [Dark/Lite theme](https://material.io/design/color/dark-theme.html)
    
* Tech stuff
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
    * [Koin](https://insert-koin.io/) for dependency injection
    * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
    * [Retrofit](https://square.github.io/retrofit/) for networking
    * [Coil](https://github.com/coil-kt/coil) for image loading
    * [Mockito](https://site.mockito.org/) for unit test

* Modern Architecture
    * Single activity architecture
    * MVVM for presentation layer
    * Modularized architecture
    * Domain layer for business logic
    * UI Layer [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) and [StateIn](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/state-in.html)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture)
    

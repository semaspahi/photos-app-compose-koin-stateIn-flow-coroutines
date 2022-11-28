package com.sema.photosapplication.ui.view.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sema.data.api.ApiService
import com.sema.data.model.PhotoResponse
import com.sema.data.repository.PhotosRepository
import com.sema.domain.usecase.getphotos.GetPhotosUseCase
import com.sema.photosapplication.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertIs
import kotlin.test.assertEquals

class AlbumsViewModelTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var repository: PhotosRepository

    @Mock
    lateinit var getPhotosUseCase: GetPhotosUseCase

    @Mock
    lateinit var viewModel: AlbumsViewModel

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = PhotosRepository(apiService)
        getPhotosUseCase = GetPhotosUseCase(repository, coroutineRule.testDispatcher)
        viewModel = AlbumsViewModel(getPhotosUseCase)
    }

    @Test
    fun uiStateAlbums_whenSuccess() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.state.collect() }
        //given
        val photoData = mock<PhotoResponse>()
        photoData.albumId=101
        whenever(repository.getPhotos()).thenReturn(listOf(photoData))
        // To make sure AlbumsUiState is success

        getPhotosUseCase.getAlbums()
        val item = viewModel.state.value
//        assertIs<AlbumsUiState.Ready>(item)
//        then

        assertEquals(AlbumsUiState.Ready(albums = listOf(photoData)), viewModel.state.value)

        collectJob.cancel()
    }

    @Test
    fun uiStateAlbums_whenInitialized_thenShowLoading() = runTest {
        assertEquals(AlbumsUiState.Loading, viewModel.state.value)
    }

}
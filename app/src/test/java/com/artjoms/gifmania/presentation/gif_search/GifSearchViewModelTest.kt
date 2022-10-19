package com.artjoms.gifmania.presentation.gif_search

import com.artjoms.gifmania.common.Constants.GIF_LIMIT
import com.artjoms.gifmania.common.Result
import com.artjoms.gifmania.domain.model.Gif
import com.artjoms.gifmania.domain.use_case.SearchForGifsUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GifSearchViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: GifSearchViewModel

    @MockK
    lateinit var searchForGifsUseCase: SearchForGifsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        viewModel = GifSearchViewModel(searchForGifsUseCase)
    }

    @Test
    fun `WHEN loading more THEN search use case called with correct params`() = runTest {
        stubSearchUseCase(Result.Success(Pair(emptyList(), true)))
        viewModel.loadMore()
        advanceUntilIdle()
        verify(exactly = 1) { searchForGifsUseCase.invoke("", GIF_LIMIT) }
    }

    @Test
    fun `WHEN success returned THEN state is updated correctly`() = runTest {
        stubSearchUseCase(Result.Success(Pair(emptyList(), true)))
        viewModel.loadMore()
        advanceUntilIdle()
        assertThat(viewModel.state.value).isEqualTo(
            GifSearchViewModel.GifSearchState(
                isLoading = false,
                gifs = emptyList(),
                shouldLoadMore = true
            )
        )
    }

    @Test
    fun `WHEN error returned THEN state is updated correctly`() = runTest {
        stubSearchUseCase(Result.Error("Error message"))
        viewModel.loadMore()
        advanceUntilIdle()
        assertThat(viewModel.state.value).isEqualTo(
            GifSearchViewModel.GifSearchState(
                error = "Error message"
            )
        )
    }

    @Test
    fun `WHEN loading returned THEN state is updated correctly`() = runTest {
        stubSearchUseCase(Result.Loading())
        viewModel.loadMore()
        advanceUntilIdle()
        assertThat(viewModel.state.value).isEqualTo(
            GifSearchViewModel.GifSearchState(
                isLoading = true
            )
        )
    }

    @Test
    fun `WHEN new query entered THEN paging values reset happen`() = runTest {
        stubSearchUseCase(Result.Loading())
        viewModel.onNewQuery("Dog")
        advanceUntilIdle()
        verify { searchForGifsUseCase.invoke("Dog", 0) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun stubSearchUseCase(result: Result<Pair<List<Gif>, Boolean>>) {
        coEvery { searchForGifsUseCase.invoke(any(), any()) }.returns(
            flowOf(
                result
            )
        )
    }
}
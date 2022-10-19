package com.artjoms.gifmania.presentation.gif_search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artjoms.gifmania.common.Constants.GIF_LIMIT
import com.artjoms.gifmania.common.Result
import com.artjoms.gifmania.domain.model.Gif
import com.artjoms.gifmania.domain.use_case.SearchForGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class GifSearchViewModel @Inject constructor(
    private val searchForGifsUseCase: SearchForGifsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(GifSearchState())
    val state: State<GifSearchState> = _state

    private val queryFlow: MutableStateFlow<String> = MutableStateFlow("")

    private var currentOffset = 0
    private var currentQuery = ""

    init {
        queryFlow
            .filter { it.isNotEmpty() }
            .debounce(300)
            .distinctUntilChanged()
            .onEach { newQuery ->
                reset(newQuery)
                fetchGifs()
            }.launchIn(viewModelScope)
    }

    fun onNewQuery(query: String) {
        queryFlow.value = query
    }

    fun loadMore() {
        currentOffset += GIF_LIMIT
        fetchGifs()
    }

    private fun reset(newQuery: String) {
        currentOffset = 0
        currentQuery = newQuery
        _state.value = GifSearchState(gifs = emptyList())
    }

    private fun fetchGifs() {
        searchForGifsUseCase(query = currentQuery, currentOffset).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        gifs = _state.value.gifs.plus(result.data.first),
                        isLoading = false,
                        shouldLoadMore = result.data.second
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    data class GifSearchState(
        val isLoading: Boolean = false,
        val gifs: List<Gif> = emptyList(),
        val error: String? = null,
        val shouldLoadMore: Boolean = true
    )
}
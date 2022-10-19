package com.artjoms.gifmania.presentation.gif_search.components

import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import com.artjoms.gifmania.domain.model.Gif
import com.artjoms.gifmania.presentation.gif_search.GifSearchViewModel

@Composable
fun GifGrid(gifs: List<Gif>, viewModel: GifSearchViewModel) {
    val state: LazyGridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(6),
    ) {
        itemsIndexed(items = gifs, span = { index, _ ->
            GridItemSpan(when (index % 10) {
                0, 1 -> 3
                5, 9 -> 6
                else -> 2
            })
        }) { _: Int, gif: Gif ->
            GifItem(gif = gif)
        }
    }

    state.onBottomReached(buffer = 10) {
        viewModel.loadMore()
    }
}
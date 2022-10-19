package com.artjoms.gifmania.presentation.gif_search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.artjoms.gifmania.presentation.gif_search.GifSearchViewModel

@Composable
fun GifSearchScreen(
    viewModel: GifSearchViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = { SearchView(viewModel = viewModel) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                GifGrid(gifs = state.gifs, viewModel = viewModel)
            }
        }
    }
}
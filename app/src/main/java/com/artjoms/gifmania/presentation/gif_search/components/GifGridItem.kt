package com.artjoms.gifmania.presentation.gif_search.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.artjoms.gifmania.R
import com.artjoms.gifmania.domain.model.Gif
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GifItem(gif: Gif) {
    GlideImage(
        model = gif.url,
        contentDescription = gif.name,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp),
        contentScale = ContentScale.FillBounds,
    ) {
        it.placeholder(R.drawable.ic_baseline_image_24)
    }
}
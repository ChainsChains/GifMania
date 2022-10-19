package com.artjoms.gifmania.data.remote.dto

data class SearchResponseDto(
    val data: List<GifDto>,
    val meta: Meta,
    val pagination: Pagination
)
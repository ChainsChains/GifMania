package com.artjoms.gifmania.domain.repository

import com.artjoms.gifmania.data.remote.dto.SearchResponseDto

interface GiphyRepository {
    suspend fun searchForGifs(query: String, offset: Int): SearchResponseDto
}
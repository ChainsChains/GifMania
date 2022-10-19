package com.artjoms.gifmania.data.repository

import com.artjoms.gifmania.common.Constants.API_KEY
import com.artjoms.gifmania.common.Constants.GIF_LIMIT
import com.artjoms.gifmania.data.remote.GiphyApi
import com.artjoms.gifmania.data.remote.dto.SearchResponseDto
import com.artjoms.gifmania.domain.repository.GiphyRepository
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val api: GiphyApi
) : GiphyRepository {
    override suspend fun searchForGifs(query: String, offset: Int): SearchResponseDto =
        api.searchGif(
            query = query,
            key = API_KEY,
            limit = GIF_LIMIT,
            offset = offset
        )
}
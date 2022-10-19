package com.artjoms.gifmania.data.remote

import com.artjoms.gifmania.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("/v1/gifs/search")
    suspend fun searchGif(
        @Query("api_key") key: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchResponseDto
}
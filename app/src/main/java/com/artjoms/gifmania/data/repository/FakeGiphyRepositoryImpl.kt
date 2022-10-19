package com.artjoms.gifmania.data.repository

import android.content.Context
import com.artjoms.gifmania.data.remote.dto.SearchResponseDto
import com.artjoms.gifmania.domain.repository.GiphyRepository
import com.google.gson.Gson
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeGiphyRepositoryImpl @Inject constructor(
    private val context: Context
) : GiphyRepository {
    override suspend fun searchForGifs(query: String, offset: Int): SearchResponseDto {
        val jsonString =
            context.assets.open("fake_giphy_search_response.json").bufferedReader().use {
                it.readText()
            }

        delay(200)


        return Gson().fromJson(jsonString, SearchResponseDto::class.java)
    }
}

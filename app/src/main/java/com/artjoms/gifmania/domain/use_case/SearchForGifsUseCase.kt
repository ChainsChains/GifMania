package com.artjoms.gifmania.domain.use_case

import com.artjoms.gifmania.common.Constants.GIF_LIMIT
import com.artjoms.gifmania.common.Result
import com.artjoms.gifmania.data.remote.dto.toGif
import com.artjoms.gifmania.domain.model.Gif
import com.artjoms.gifmania.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchForGifsUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    operator fun invoke(query: String, offset: Int): Flow<Result<Pair<List<Gif>, Boolean>>> = flow {
        try {
            emit(Result.Loading())
            val gifs = repository.searchForGifs(query = query, offset = offset)
            emit(
                Result.Success(
                    Pair(
                        gifs.data.map { it.toGif() },
                        (gifs.pagination.offset + GIF_LIMIT) < gifs.pagination.total_count
                    )
                )
            )
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "Unexpected error."))
        } catch (e: IOException) {
            emit(Result.Error(e.localizedMessage ?: "Check you internet connection."))
        }
    }
}
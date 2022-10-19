package com.artjoms.gifmania.domain.use_case

import com.artjoms.gifmania.common.Result
import com.artjoms.gifmania.data.repository.FakeGiphyRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchForGifsUseCaseTest {
    private lateinit var searchForGifsUseCase: SearchForGifsUseCase
    private lateinit var fakeGiphyRepository: FakeGiphyRepository

    @Before
    fun setUp() {
        fakeGiphyRepository = FakeGiphyRepository()
        searchForGifsUseCase = SearchForGifsUseCase(fakeGiphyRepository)
    }

    @Test
    fun `WHEN use case invoked THEN ResultLoading emitted`() = runBlocking {
        val result = searchForGifsUseCase("", 0).toList().first()
        assertThat(result).isInstanceOf(Result.Loading::class.java)
    }

    @Test
    fun `WHEN repository returns Http exception THEN ResultError with correct message emitted`() = runBlocking {
            val result = searchForGifsUseCase("http", 0).toList().last()
            assertThat(result).isInstanceOf(Result.Error::class.java)
            assertThat((result as Result.Error).message).isEqualTo("HTTP 500 Response.error()")
        }

    @Test
    fun `WHEN repository returns IO exception THEN ResultError with correct message emitted`() = runBlocking {
            val result = searchForGifsUseCase("io", 0).toList().last()
            assertThat(result).isInstanceOf(Result.Error::class.java)
            assertThat((result as Result.Error).message).isEqualTo("Check you internet connection.")
        }

    @Test
    fun `WHEN there is more gifs to fetch THEN return true`() = runBlocking {
        val result = searchForGifsUseCase("", 0).toList().last()
        assertThat((result as Result.Success).data.second).isTrue()
    }

    @Test
    fun `WHEN repository returned last gifs THEN return false`() = runBlocking {
        val result = searchForGifsUseCase("last", 0).toList().last()
        assertThat((result as Result.Success).data.second).isFalse()
    }
}
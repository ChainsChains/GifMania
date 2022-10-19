package com.artjoms.gifmania.data.repository

import com.artjoms.gifmania.common.Constants.GIF_LIMIT
import com.artjoms.gifmania.data.remote.dto.*
import com.artjoms.gifmania.domain.repository.GiphyRepository
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class FakeGiphyRepository : GiphyRepository {
    override suspend fun searchForGifs(query: String, offset: Int): SearchResponseDto {
        return when (query) {
            "http" -> throw HttpException(Response.error<String>(500, ResponseBody.create(null, "")))
            "io" -> throw IOException("Check you internet connection.")
            "last" -> SearchResponseDto(
                data = buildList(100) {
                    createGifDto()
                },
                meta = Meta("", "", 200),
                pagination = Pagination(GIF_LIMIT, 100 - GIF_LIMIT, 100)
            )
            else -> {
                SearchResponseDto(
                    data = buildList(100) {
                        createGifDto()
                    },
                    meta = Meta("", "", 200),
                    pagination = Pagination(20, 0, 100)
                )
            }
        }
    }
}

private fun createGifDto() = GifDto(
    analytics = Analytics(
        onclick = Onclick(""),
        onload = Onload(""),
        onsent = Onsent("")
    ),
    analytics_response_payload = "",
    bitly_gif_url = "",
    bitly_url = "",
    content_url = "",
    embed_url = "",
    id = "",
    images = Images(
        WStill("", "", "", ""),
        Downsized("", "", "", ""),
        DownsizedLarge("", "", "", ""),
        DownsizedMedium("", "", "", ""),
        DownsizedSmall("", "", "", ""),
        DownsizedStill("", "", "", ""),
        FixedHeight("", "", "", "", "", "", "", ""),
        FixedHeightDownsampled("", "", "", "", "", ""),
        FixedHeightSmall("", "", "", "", "", "", "", ""),
        FixedHeightSmallStill("", "", "", ""),
        FixedHeightStill("", "", "", ""),
        FixedWidth("", "", "", "", "", "", "", ""),
        FixedWidthDownsampled("", "", "", "", "", ""),
        FixedWidthSmall("", "", "", "", "", "", "", ""),
        FixedWidthSmallStill("", "", "", ""),
        FixedWidthStill("", "", "", ""),
        Hd("", "", "", ""),
        Looping("", ""),
        Original("", "", "", "", "", "", "", "", "", ""),
        OriginalMp4("", "", "", ""),
        OriginalStill("", "", "", ""),
        Preview("", "", "", ""),
        PreviewGif("", "", "", ""),
        PreviewWebp("", "", "", "")
    ),
    import_datetime = "",
    is_sticker = 0,
    rating = "",
    slug = "",
    source = "",
    source_post_url = "",
    source_tld = "",
    title = "",
    trending_datetime = "",
    type = "",
    url = "",
    user = User(
        "", "", "", "", "", "", true, "", "", ""
    ),
    username = ""
)
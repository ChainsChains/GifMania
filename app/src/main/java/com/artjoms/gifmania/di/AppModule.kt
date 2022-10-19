package com.artjoms.gifmania.di

import com.artjoms.gifmania.common.Constants
import com.artjoms.gifmania.data.remote.GiphyApi
import com.artjoms.gifmania.data.repository.GiphyRepositoryImpl
import com.artjoms.gifmania.domain.repository.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGiphyApi(): GiphyApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GiphyApi::class.java)

    @Provides
    @Singleton
    fun provideGiphyRepository(api: GiphyApi): GiphyRepository = GiphyRepositoryImpl(api)

//    @Provides
//    @Singleton
//    fun provideGiphyRepository(@ApplicationContext context: Context): GiphyRepository =
//        FakeGiphyRepositoryImpl(context)
}
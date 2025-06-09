package com.app.omnipro_test_rm.di

import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.okHttpClient
import com.app.omnipro_test_rm.data.local.database.AppDatabase
import com.app.omnipro_test_rm.data.remote.GraphQLDataSource
import com.app.omnipro_test_rm.data.repository.CharacterRepository
import com.app.omnipro_test_rm.domain.repository.ICharacterRepository
import com.app.omnipro_test_rm.domain.usecases.GetCharacterDetailUseCase
import com.app.omnipro_test_rm.domain.usecases.GetCharactersUseCase
import com.app.omnipro_test_rm.domain.usecases.ToggleFavoriteUseCase
import com.app.omnipro_test_rm.ui.screens.characters.CharactersViewModel
import com.app.omnipro_test_rm.ui.screens.detail.CharacterDetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Apollo Client
    single {
        val cacheFactory = SqlNormalizedCacheFactory("apollo.db")

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .okHttpClient(okHttpClient)
            .normalizedCache(cacheFactory)
            .build()
    }


    // Database
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "rick_morty_database"
        ).build()
    }
    
    single { get<AppDatabase>().characterDao() }
    
    // Data Sources
    single { GraphQLDataSource(get()) }
    
    // Repository
    single<ICharacterRepository> { CharacterRepository(get(), get()) }
    
    // Use Cases
    single { GetCharactersUseCase(get()) }
    single { GetCharacterDetailUseCase(get()) }
    single { ToggleFavoriteUseCase(get()) }
    
    // ViewModels
    viewModel { CharactersViewModel(get(), get()) }
    viewModel { parameters -> CharacterDetailViewModel(parameters.get(), get(), get()) }
}
package com.salanevich.githubapp

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.salanevich.githubapp.data.database.AppDatabase
import com.salanevich.githubapp.data.network.GitHubApi
import com.salanevich.githubapp.domain.UsersRepository
import com.salanevich.githubapp.data.repo.UsersRepositoryImpl
import com.salanevich.githubapp.data.usecase.GetUsersUseCaseImpl
import com.salanevich.githubapp.data.usecase.SearchUseCaseImpl
import com.salanevich.githubapp.domain.usecase.GetUsersUseCase
import com.salanevich.githubapp.domain.usecase.SearchUseCase
import com.salanevich.githubapp.ui.screen.main.MainViewModel
import com.salanevich.githubapp.ui.screen.detail.DetailViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    factory {
        val json = Json {
            isLenient = true
            prettyPrint = true
            prettyPrintIndent = "  "
            ignoreUnknownKeys = true
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    .build()
                it.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(GitHubApi::class.java)
    }

    factory {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "db").build()
    }

    single<UsersRepository> { UsersRepositoryImpl(get(), get()) }

    single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
    single<SearchUseCase> { SearchUseCaseImpl() }

    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)

}
package com.pjlapps.guardiannews.deps

import android.content.Context
import androidx.room.Room
import com.pjlapps.guardiannews.BuildConfig
import com.pjlapps.guardiannews.data.AppDatabase
import com.pjlapps.guardiannews.data.NewsRepository
import com.pjlapps.guardiannews.data.NewsRepositoryImpl
import com.pjlapps.guardiannews.network.GuardianRemoteDataSource
import com.pjlapps.guardiannews.network.GuardianApiService
import com.pjlapps.guardiannews.network.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [AppModule.DataModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    @Named("guardianApiKey")
    fun provideGuardianApiKey(): String {
        return BuildConfig.GUARDIAN_API_KEY
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://content.guardianapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGuardianApiService(
        retrofit: Retrofit
    ): GuardianApiService {
        return retrofit.create(GuardianApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGuardianApi(
        apiService: GuardianApiService,
        @Named("guardianApiKey") guardianApiKey: String
    ): GuardianRemoteDataSource {
        return GuardianRemoteDataSource(
            apiService,
            guardianApiKey
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataModule {
        @Binds
        @Singleton
        fun bindNewsRepository(
            newsRepositoryImpl: NewsRepositoryImpl
        ): NewsRepository

        @Binds
        @Singleton
        fun bindRemoteDataSource(
            remoteDataSource: GuardianRemoteDataSource
        ): RemoteDataSource
    }
}
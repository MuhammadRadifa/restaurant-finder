package com.dicoding.core.di

import androidx.room.Room
import com.dicoding.core.data.source.local.preferences.SettingPreferences
import com.dicoding.core.data.source.local.preferences.dataStore
import com.dicoding.core.data.source.local.room.RestaurantDatabase
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.data.source.remote.network.ApiConfig
import com.dicoding.core.domain.repository.IRestaurantRepository
import com.dicoding.core.data.source.RestaurantRepository
import com.dicoding.core.data.source.local.LocalDataSource
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module{
    factory { get<RestaurantDatabase>().favoriteDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("restaurant".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            RestaurantDatabase::class.java, "Restaurant.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val preferencesModule = module {
    single {
        val settingPreferences = SettingPreferences(androidContext().dataStore)
        settingPreferences
    }
}

val networkModule = module {
    single {
        val apiService = ApiConfig.getApiService()
        apiService
    }
}

val repositoryModule = module {
    single {
        RemoteDataSource(get())
    }
    single {
        LocalDataSource(get(), get())
    }
    single<IRestaurantRepository> {
        RestaurantRepository(get(), get())
    }
}
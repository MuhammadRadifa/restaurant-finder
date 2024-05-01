package com.dicoding.restaurantfinder.presentation

import android.app.Application
import com.dicoding.core.di.databaseModule
import com.dicoding.core.di.networkModule
import com.dicoding.core.di.preferencesModule
import com.dicoding.core.di.repositoryModule
import com.dicoding.restaurantfinder.di.useCaseModule
import com.dicoding.restaurantfinder.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModel,
                    preferencesModule,
                    databaseModule
                )
            )
        }
    }
}
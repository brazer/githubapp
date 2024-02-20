package com.salanevich.githubapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitHubApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GitHubApp)
            modules(appModule)
        }
    }
}
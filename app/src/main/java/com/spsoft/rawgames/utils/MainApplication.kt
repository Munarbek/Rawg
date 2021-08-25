package com.spsoft.rawgames.utils

import android.app.Application
import com.spsoft.rawgames.data.apiservice.ApiService
import com.spsoft.rawgames.ui.home.HomeRepository
import com.spsoft.rawgames.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))
        bind() from singleton { ApiService() }
        bind<HomeRepository>() with singleton { HomeRepository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
    }
}

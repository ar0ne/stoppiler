package com.ar0ne.stoppiler.di

import com.ar0ne.stoppiler.storage.AppDataStorage
import com.ar0ne.stoppiler.services.GoodsSamplesService
import org.koin.dsl.module


val storageModule = module {
    single { AppDataStorage(get()) }
    single { GoodsSamplesService() }
}

val appModules = listOf(
    storageModule
)
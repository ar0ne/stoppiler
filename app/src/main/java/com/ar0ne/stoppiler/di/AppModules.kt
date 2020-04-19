package com.ar0ne.stoppiler.di

import com.ar0ne.stoppiler.storage.AppDataStorage
import org.koin.dsl.module


val storageModule = module {
    single { AppDataStorage(get()) }
}

val appModules = listOf(
    storageModule
)
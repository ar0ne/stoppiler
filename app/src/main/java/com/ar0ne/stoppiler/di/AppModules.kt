package com.ar0ne.stoppiler.di

import com.ar0ne.stoppiler.storage.UserDataStorage
import org.koin.dsl.module


val storageModule = module {
    single { UserDataStorage(get()) }
}

val appModules = listOf(
    storageModule
)
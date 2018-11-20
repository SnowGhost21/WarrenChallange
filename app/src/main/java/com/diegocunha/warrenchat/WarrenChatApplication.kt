package com.diegocunha.warrenchat

import android.app.Application
import com.diegocunha.warrenchat.injection.appModules
import org.koin.android.ext.android.startKoin

class WarrenChatApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModules))
    }
}
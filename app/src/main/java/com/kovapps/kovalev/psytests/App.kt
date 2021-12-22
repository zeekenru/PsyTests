package com.kovapps.kovalev.psytests

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kovapps.kovalev.psytests.di.AppModule
import com.kovapps.kovalev.psytests.di.Scopes
import io.paperdb.Paper
import toothpick.Toothpick


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Toothpick.openScope(Scopes.APP_SCOPE).installModules(AppModule(this))
        Paper.init(this)
//        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(listOf("F04BA8FA54F48B6BB53AC70C3479A3F0")).build())
        MobileAds.initialize(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
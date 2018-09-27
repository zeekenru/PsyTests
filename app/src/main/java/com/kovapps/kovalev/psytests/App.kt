package com.kovapps.kovalev.psytests

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.MobileAds
import com.kovapps.kovalev.psytests.di.AppModule
import com.kovapps.kovalev.psytests.di.Scopes
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import io.paperdb.Paper
import toothpick.Toothpick


private const val TAG = "MYTAG"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val formatStrategy = PrettyFormatStrategy.newBuilder().tag(TAG).build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Toothpick.openScope(Scopes.APP_SCOPE).installModules(AppModule(this))
        Paper.init(this)
        MobileAds.initialize(this, "ca-app-pub-8407125762950584~6538583611")
    }
}
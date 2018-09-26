package com.kovapps.kovalev.psytests.di

import android.content.Context
import com.kovapps.kovalev.psytests.model.AssetsTestDao
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.TestDao
import com.orhanobut.logger.Logger
import toothpick.config.Module


class AppModule(context: Context) : Module() {

    init {
        Logger.d("init app module")
        bind(Context::class.java).toInstance(context)
        bind(TestDao::class.java).to(AssetsTestDao::class.java)
        bind(PreferenceHelper::class.java).to(PreferenceHelper::class.java)
    }
}
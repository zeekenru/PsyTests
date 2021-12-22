package com.kovapps.kovalev.psytests.di

import android.content.Context
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.model.ResourcesTestDao
import com.kovapps.kovalev.psytests.model.TestDao
import toothpick.config.Module


class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(TestDao::class.java).to(ResourcesTestDao::class.java)
        bind(PreferenceHelper::class.java).to(PreferenceHelper::class.java)
    }
}
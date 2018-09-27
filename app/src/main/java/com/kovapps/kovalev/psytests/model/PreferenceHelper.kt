package com.kovapps.kovalev.psytests.model

import android.content.Context
import android.content.SharedPreferences
import com.orhanobut.logger.Logger
import javax.inject.Inject


class PreferenceHelper @Inject constructor(val context: Context) {


    companion object {
        private const val SP_NAME = "com.kovapss.psy.sp"
        private const val FIRST_LAUNCH_KEY = "first_launch"
        private const val SAVE_RESULTS_ENABLED_KEY = "save_results_enabled"
    }

    private val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun isFirstLaunch(isFirstLaunch: Boolean) {
        sp.edit().putBoolean(FIRST_LAUNCH_KEY, isFirstLaunch).apply()
    }

    fun isFirstLaunch(): Boolean = sp.getBoolean(FIRST_LAUNCH_KEY, true)

    fun saveResultsEnabled(enabled: Boolean) =
        sp.edit().putBoolean(SAVE_RESULTS_ENABLED_KEY, enabled).apply()


    fun saveResultsEnabled(): Boolean = sp.getBoolean(SAVE_RESULTS_ENABLED_KEY, true)
}
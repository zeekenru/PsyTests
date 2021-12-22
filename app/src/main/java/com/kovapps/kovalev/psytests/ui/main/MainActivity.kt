package com.kovapps.kovalev.psytests.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.ui.home.HomeFragment
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    companion object {
        private const val WARNING_FRAGMENT_TAG = "warning"
        private const val HOME_FM_TAG = "home"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        if (preferenceHelper.isFirstLaunch()) {
            showWarningFragment()
            preferenceHelper.isFirstLaunch(false)
        } else {
            showHomeScreen()
        }
    }

    fun onContinueBtnClicked() {
        showHomeScreen()
    }

    private fun showHomeScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment(), HOME_FM_TAG)
            .commit()
    }

    private fun showWarningFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, IntroFragment(), WARNING_FRAGMENT_TAG)
            .commit()
    }

}


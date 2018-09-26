package com.kovapps.kovalev.psytests.ui

import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kovapps.kovalev.psytests.BuildConfig
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_settings.*
import toothpick.Toothpick
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        setSupportActionBar(settings_toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }
        version_value.text = BuildConfig.VERSION_NAME
        save_switch.isChecked = preferenceHelper.saveResultsEnabled()
        save_switch.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) preferenceHelper.saveResultsEnabled(true).apply { Logger.d("save enaled") }
            else preferenceHelper.saveResultsEnabled(false).apply { Logger.d("save disabled") }
        }

        user_libraries.setOnClickListener {
            LibsBuilder()
                    .withAutoDetect(true)
                    .withLicenseShown(true)
                    .withActivityTitle("Библиотеки")
                    .withActivityStyle(Libs.ActivityStyle.LIGHT)
                    .start(this)
        }
        send_review.setOnClickListener {
            ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .addEmailTo("zeekenru@gmail.com")
                    .setSubject("PsyTest issue")
                    .startChooser()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}

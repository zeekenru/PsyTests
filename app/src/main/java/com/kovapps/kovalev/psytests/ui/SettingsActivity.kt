package com.kovapps.kovalev.psytests.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
            if (isChecked) preferenceHelper.saveResultsEnabled(true)
            else preferenceHelper.saveResultsEnabled(false)
        }

        review.setOnClickListener {
            LibsBuilder()
                    .withAutoDetect(true)
                    .withLicenseShown(true)
                    .withActivityTitle(getString(R.string.libs))
                    .withActivityStyle(Libs.ActivityStyle.LIGHT)
                    .start(this)
        }
        send_review.setOnClickListener {
            ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .addEmailTo("zeekenru@gmail.com")
                    .setSubject(getString(R.string.email_topic))
                    .startChooser()
        }
        review.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("market://details?id=$packageName"))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                val intent = Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://play.google.com/store/apps/details?id==$packageName"))
                startActivity(intent)
            }
        }
        privacy_police.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1Dlt4cGz-V3wP3UXUY0LPwKErWBxuE_xHOBnp5BYQ-Zc/edit?usp=sharing"))
            startActivity(intent)
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

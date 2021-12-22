package com.kovapps.kovalev.psytests.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kovapps.kovalev.psytests.BuildConfig
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import toothpick.Toothpick
import javax.inject.Inject


class SettingsFragment : Fragment() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var saveResultCheckBox: CheckBox
    private lateinit var privacyBtn: TextView
    private lateinit var rateBtn: TextView
    private lateinit var contactBtn: View
    private lateinit var backBtn: ImageView
    private lateinit var versionText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        with(view) {
            saveResultCheckBox = findViewById(R.id.save_res_checkbox)
            privacyBtn = findViewById(R.id.privacy)
            rateBtn = findViewById(R.id.rate)
            contactBtn = findViewById(R.id.contact)
            backBtn = findViewById(R.id.settings_back_btn)
            versionText = findViewById(R.id.version_text)
        }
        privacyBtn.setOnClickListener {
            val intent = Intent(Intent.CATEGORY_BROWSABLE)
                .apply {
                    data = Uri.parse(getString(R.string.privacy_link))
                    action = Intent.ACTION_VIEW
                }
            startActivity(intent)
        }
        rateBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}"))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                val intent = Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"))
                startActivity(intent)
            }
        }
        contactBtn.setOnClickListener {
            val selectorIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
            }
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, "zeekenru@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "PsyTests")
                selector = selectorIntent
            }
            startActivity(Intent.createChooser(emailIntent,"Send feedback"))
        }
        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        saveResultCheckBox.isChecked = preferenceHelper.saveResultsEnabled()
        saveResultCheckBox.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) preferenceHelper.saveResultsEnabled(true)
            else preferenceHelper.saveResultsEnabled(false)
        }
        versionText.text = BuildConfig.VERSION_NAME

    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

}
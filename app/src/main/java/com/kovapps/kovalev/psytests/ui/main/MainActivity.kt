package com.kovapps.kovalev.psytests.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kovapps.kovalev.psytests.R
import com.kovapps.kovalev.psytests.di.Scopes
import com.kovapps.kovalev.psytests.dialogs.WarningFragment
import com.kovapps.kovalev.psytests.model.PreferenceHelper
import com.kovapps.kovalev.psytests.ui.SettingsActivity
import com.kovapps.kovalev.psytests.ui.history.HistoryFragment
import com.kovapps.kovalev.psytests.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject lateinit var preferenceHelper : PreferenceHelper

    companion object {
        private const val WARNING_FRAGMENT_TAG = "warning"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP_SCOPE))
        setSupportActionBar(main_toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.navigation_home -> showHomeScreen()
                R.id.navigation_history -> showHistoryScreen()
                else -> {}
            }
            true
        }
        showHomeScreen()
        if (preferenceHelper.isFirstLaunch()){
            showWarningFragment()
            preferenceHelper.isFirstLaunch(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_settings -> {
                showSettingsScreen()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showHomeScreen() {
        showFragment(HomeFragment())
    }

    private fun showHistoryScreen() {
        showFragment(HistoryFragment())
    }

    private fun showWarningFragment() {
        WarningFragment().show(supportFragmentManager, WARNING_FRAGMENT_TAG)
    }

    private fun showSettingsScreen() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun showFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container_layout, fragment).commit()
    }
}


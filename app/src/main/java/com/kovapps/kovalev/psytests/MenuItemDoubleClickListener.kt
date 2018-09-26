package com.kovapps.kovalev.psytests

import android.view.View


abstract class MenuItemDoubleClickListener : View.OnClickListener {

    private val timeDelta = 300

    var lastClickTime = 0L

    abstract fun onSingleClick(item: View)

    abstract fun onDoubleClick(item: View)

    override fun onClick(item: View) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < timeDelta) {
            onDoubleClick(item)
        } else {
            onSingleClick(item)
        }
        lastClickTime = clickTime


    }


}
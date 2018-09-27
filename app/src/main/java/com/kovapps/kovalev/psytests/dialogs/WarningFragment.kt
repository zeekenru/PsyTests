package com.kovapps.kovalev.psytests.dialogs


import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import com.kovapps.kovalev.psytests.R

class WarningFragment : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.warning))
                .setMessage("""Результаты и интерпретации, полученные без участия специалистов, не следует воспринимать слишком серьезно.
Диагностическую ценность имеют только исследования, проведенные профессиональным психологом""")
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }
                .create()

    }
}

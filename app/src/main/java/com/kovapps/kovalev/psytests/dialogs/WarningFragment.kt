package com.kovapps.kovalev.psytests.dialogs


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class WarningFragment : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
                .setTitle("Предупреждение")
                .setMessage("Результаты и интерпретации, полученные без участия специалистов, не следует воспринимать слишком серьезно. \n" +
                        "Диагностическую ценность имеют только исследования, проведенные профессиональным психологом")
                .setPositiveButton("Ок") { dialog, _ -> dialog.cancel() }
                .create()

    }
}

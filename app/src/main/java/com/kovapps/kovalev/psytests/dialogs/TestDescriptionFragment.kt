package com.kovapps.kovalev.psytests.dialogs


import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment

class TestDescriptionFragment : AppCompatDialogFragment() {

    companion object {
        const val DESCRIPTION_PARAM = "description"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var description = ""
        if (savedInstanceState != null && savedInstanceState.containsKey(DESCRIPTION_PARAM)) {
            description = savedInstanceState.getString(DESCRIPTION_PARAM)!!
        } else if (arguments != null && arguments!!.containsKey(DESCRIPTION_PARAM)){
            description = arguments!!.getString(DESCRIPTION_PARAM)
        }
        return AlertDialog.Builder(context!!)
                .setMessage(description)
                .setPositiveButton("Закрыть") { dialog, _ -> dialog.cancel() }
                .create()
    }
}

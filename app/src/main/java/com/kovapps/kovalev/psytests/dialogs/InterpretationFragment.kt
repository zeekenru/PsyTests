package com.kovapps.kovalev.psytests.dialogs


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.kovapps.kovalev.psytests.R
import com.orhanobut.logger.Logger


class InterpretationFragment : DialogFragment() {

    companion object {
        const val MESSAGE_PARAM = "message"
        fun getInstance(message: String): InterpretationFragment =
                InterpretationFragment().apply {
                    arguments = Bundle().apply { putString(MESSAGE_PARAM, message) }
                }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var message = ""
        if (arguments != null) {
            message = arguments!!.getString(MESSAGE_PARAM)!!
        }
        return AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.interpretation))
                .setMessage(message)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }
                .create()
    }


}

package com.kovapps.kovalev.psytests.dialogs


import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.kovapps.kovalev.psytests.R
import com.orhanobut.logger.Logger


class DeleteAllDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
                .setTitle(R.string.delete_all_results)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, activity!!.intent)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.no){ dialog, _ ->
                    targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, activity!!.intent)
                    dialog.cancel()
                }
                .create()
    }


}

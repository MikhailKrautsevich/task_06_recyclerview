package com.example.recyclerviewapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.recyclerviewapp.DataSupplier
import com.example.recyclerviewapp.R
import java.lang.IllegalStateException

class DeleteContactDialog : DialogFragment() {

    companion object {
        private const val ARGS_POS = "ARGS_POS1"

        fun newDeleteContactDialog(pos: Int): DeleteContactDialog {
            val fragment = DeleteContactDialog()
            val bundle = Bundle()
            bundle.putInt(ARGS_POS, pos)
            fragment.arguments = bundle
            return fragment
        }

        fun getContactPosFromArgs(bundle: Bundle): Int = bundle.getInt(ARGS_POS)
    }

    private var dataSource: DataSupplier? = null
    private var contactPos = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataSupplier) {
            dataSource = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            contactPos = getContactPosFromArgs(it)
        }
        var message = ""
        if (contactPos >= 0) {
            dataSource?.let {
                val contact = it.getContactByPosition(contactPos)
                if (contact != null) {
                    message = "${contact.name} ${contact.lastname}"
                }
            }
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.delete_con_dialog)).setMessage(message)
                .setPositiveButton(
                    getString(R.string.delete_con)
                ) { _, _ ->
                    dataSource?.deleteContactByPos(contactPos)
                    setFragmentResult(RecyclerFragment.REQUEST_KEY, Bundle())
                    dialog?.cancel()
                }.setNegativeButton(
                    getString(R.string.cancel)
                ) { _, _ -> dialog?.cancel() }
            builder.create()
        } ?: throw IllegalStateException("Activity is null")
    }

    override fun onDetach() {
        super.onDetach()
        dataSource = null
    }

}
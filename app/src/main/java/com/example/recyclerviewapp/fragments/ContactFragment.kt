package com.example.recyclerviewapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.recyclerviewapp.DataSupplier
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.data.ContactData
import com.squareup.picasso.Picasso

class ContactFragment : Fragment(R.layout.fragment_contact) {

    companion object {
        private const val ARGS_POS = "ARGS_POS"

        fun newContactFragment(pos: Int): ContactFragment {
            val fragment = ContactFragment()
            val bundle = Bundle()
            bundle.putInt(ARGS_POS, pos)
            fragment.arguments = bundle
            return fragment
        }

        fun getContactPosFromArgs(bundle: Bundle): Int = bundle.getInt(ARGS_POS)
    }

    private var dataSource: DataSupplier? = null
    private var textName: TextView? = null
    private var textLastName: TextView? = null
    private var textNumber: TextView? = null
    private var userPic: ImageView? = null
    private var contact: ContactData? = null
    private var contactPosition: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataSupplier) {
            dataSource = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactPosition = getContactPosFromArgs(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contact, container, false)
        textName = v.findViewById(R.id.text_name)
        textLastName = v.findViewById(R.id.text_lastname)
        textNumber = v.findViewById(R.id.text_number)
        userPic = v.findViewById(R.id.userPicBig)
        return v
    }

    override fun onStart() {
        super.onStart()
        dataSource?.let {
            if (contactPosition >= 0) {
                contact = it.getContactByPosition(contactPosition)
            }
        }
        contact?.let {
            setData(it)
        }
    }

    private fun setData(con: ContactData) {
        textName?.text = con.name
        textLastName?.text = con.lastname
        textNumber?.text = con.number
        Picasso.get().load(con.getBigPicURL().toUri())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder_error)
            .into(userPic)
    }

    override fun onDetach() {
        super.onDetach()
        dataSource = null
    }
}
package com.example.recyclerviewapp.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.recyclerviewapp.DataSupplier
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.data.ContactData
import com.squareup.picasso.Picasso

class ContactFragment : Fragment(R.layout.fragment_contact) {

    companion object {
        private const val ARGS_ID = "ARGS_id111"

        fun newContactFragment(id: Int): ContactFragment {
            val fragment = ContactFragment()
            val bundle = Bundle()
            bundle.putInt(ARGS_ID, id)
            fragment.arguments = bundle
            return fragment
        }

        fun getContactIdFromArgs(bundle: Bundle): Int {
            return bundle.getInt(ARGS_ID)
        }
    }

    private var dataSource: DataSupplier? = null
    private var editName: EditText? = null
    private var editLastName: EditText? = null
    private var editNumber: EditText? = null
    private var userPic: ImageView? = null
    private var contact: ContactData? = null
    private var btnConfirm: Button? = null
    private var contactId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataSupplier) {
            dataSource = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactId = getContactIdFromArgs(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contact, container, false)
        editName = v.findViewById(R.id.edit_name)
        editLastName = v.findViewById(R.id.edit_lastname)
        editNumber = v.findViewById(R.id.edit_number)
        userPic = v.findViewById(R.id.userPicBig)
        btnConfirm = v.findViewById(R.id.btn_confirm)
        return v
    }

    override fun onStart() {
        super.onStart()
        dataSource?.let {
            if (contactId >= 0) {
                contact = it.getContactById(contactId)
            }
        }
        contact?.let {
            setData(it)
            btnConfirm?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    it.apply {
                        name = editName?.text?.toString().toString()
                        lastname = editLastName?.text?.toString().toString()
                        number = editNumber?.text?.toString().toString()
                    }
                    dataSource?.replaceContact(it)
                    setFragmentResult(RecyclerFragment.REQUEST_KEY, Bundle())
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })
        }
    }

    private fun setData(con: ContactData) {
        val factory = Editable.Factory.getInstance()
        editName?.text = factory.newEditable(con.name)
        editLastName?.text = factory.newEditable(con.lastname)
        editNumber?.text = factory.newEditable(con.number)
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
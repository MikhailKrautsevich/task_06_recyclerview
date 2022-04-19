package com.example.recyclerviewapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.DataSupplier
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.data.ContactData
import com.squareup.picasso.Picasso

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private var dataSource: DataSupplier? = null
    private var recyclerView: RecyclerView? = null
    private val picasso = Picasso.get()

    companion object {
        fun newRecyclerFragment() = RecyclerFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataSupplier) {
            dataSource = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_recycler, container, false)
        recyclerView = v.findViewById(R.id.recycler_contacts)
        return v
    }

    override fun onStart() {
        super.onStart()
        recyclerView?.let {
            it.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        }
        dataSource?.let {
            recyclerView?.adapter = ContactAdapter(it.getContacts())
        }
    }

    override fun onDetach() {
        super.onDetach()
        dataSource = null
    }

    inner class ContactAdapter(private val contacts: List<ContactData>) : RecyclerView
    .Adapter<ContactAdapter.ContactHolder>() {

        inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            private val contactPic: ImageView = itemView.findViewById(R.id.userPic)
            private val contactInfo: TextView = itemView.findViewById(R.id.contactData)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(p0: View?) {
                Log.d("123456", "Click")
            }

            fun bind(contact: ContactData) {
                picasso.load(contact.getSmallPicURL().toUri())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder_error)
                    .into(contactPic)
                contactInfo.text = contact.toString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_contact, parent, false)
            return ContactHolder(itemView)
        }

        override fun getItemCount() = contacts.size

        override fun onBindViewHolder(holder: ContactHolder, position: Int) {
            holder.bind(contacts[position])
        }
    }
}
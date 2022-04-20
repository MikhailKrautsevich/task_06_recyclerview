package com.example.recyclerviewapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.DataSupplier
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.data.ContactData
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class RecyclerFragment : Fragment(R.layout.fragment_recycler) {

    private val picasso = Picasso.get()

    private var dataSource: DataSupplier? = null
    private var recyclerView: RecyclerView? = null
    private var curLayoutManager: LinearLayoutManager? = null
    private var currentHolderPosition = -1
    private var tempList: MutableList<ContactData> = ArrayList()

    companion object {
        const val REQUEST_KEY = "123R"
        fun newRecyclerFragment() = RecyclerFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataSupplier) {
            dataSource = context
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.search_menu_view)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tempList.clear()
                val allContacts = dataSource?.getContacts()
                p0?.let { text ->
                    val textToSearch: String = text.lowercase(Locale.getDefault())
                    if (text.isNotBlank()) {
                        allContacts?.let {
                            for (i in 0 until allContacts.size) {
                                if ("${allContacts[i].name} ${allContacts[i].lastname}".lowercase()
                                        .contains(
                                            textToSearch
                                        )
                                ) {
                                    tempList.add(allContacts[i])
                                }
                                recyclerView?.adapter = ContactAdapter(tempList)
                                recyclerView?.adapter?.notifyDataSetChanged()
                            }
                        }
                    } else {
                        allContacts?.let {
                            recyclerView?.adapter = ContactAdapter(it)
                            recyclerView?.adapter?.notifyDataSetChanged()
                        }
                    }
                }
                return false
            }
        })
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setFragmentResultListener(REQUEST_KEY) { _, _ ->
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()
        recyclerView?.let {
            curLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            it.layoutManager = curLayoutManager
        }
        dataSource?.let {
            recyclerView?.adapter = ContactAdapter(it.getContacts())
        }
        if (currentHolderPosition > 0) {
            recyclerView?.scrollToPosition(currentHolderPosition)
        }
    }

    override fun onStop() {
        super.onStop()
        curLayoutManager?.let {
            currentHolderPosition = it.findFirstVisibleItemPosition()
        }
    }

    override fun onDetach() {
        super.onDetach()
        dataSource = null
    }

    inner class ContactAdapter(private val contacts: List<ContactData>) : RecyclerView
    .Adapter<ContactAdapter.ContactHolder>() {

        inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {
            private val contactPic: ImageView = itemView.findViewById(R.id.userPic)
            private val contactInfo: TextView = itemView.findViewById(R.id.contactData)

            init {
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }

            override fun onClick(p0: View?) {
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    ContactFragment.newContactFragment(adapterPosition),
                    null
                ).addToBackStack("TAG").commit()
            }

            fun bind(contact: ContactData) {
                picasso.load(contact.getSmallPicURL().toUri())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder_error)
                    .into(contactPic)
                contactInfo.text = contact.toString()
            }

            override fun onLongClick(p0: View?): Boolean {
                val dialog = DeleteContactDialog.newDeleteContactDialog(adapterPosition)
                dialog.show(requireActivity().supportFragmentManager, "TAG!")
                return true
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
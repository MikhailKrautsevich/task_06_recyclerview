package com.example.recyclerviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewapp.data.ContactData
import com.example.recyclerviewapp.data.DataModel
import com.example.recyclerviewapp.fragments.RecyclerFragment
import java.util.ArrayList

class MainActivity : AppCompatActivity(), DataSupplier {

    private var model: DataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = DataModel(context = applicationContext)

        supportFragmentManager.beginTransaction().run {
            replace(
                R.id.fragment_container,
                RecyclerFragment.newRecyclerFragment(),
                null
            )
            commit()
        }
    }

    override fun getContacts(): ArrayList<ContactData> {
        var list: ArrayList<ContactData> = ArrayList()
        model?.let {
            list = it.getContacts()
        }
        return list
    }

    override fun getContactById(id: Int): ContactData? {
        var contact: ContactData? = null
        model?.let {
            contact = it.getContById(id)
        }
        return contact
    }

    override fun deleteContactById(id: Int) {
        model?.deleteContact(id)
    }

    override fun replaceContact(contact: ContactData) {
        model?.replaceContact(contact)
    }
}
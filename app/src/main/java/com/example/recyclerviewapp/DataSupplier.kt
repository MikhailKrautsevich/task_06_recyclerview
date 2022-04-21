package com.example.recyclerviewapp

import com.example.recyclerviewapp.data.ContactData

interface DataSupplier {
    fun getContacts(): ArrayList<ContactData>
    fun getContactById(id: Int): ContactData?
    fun deleteContactById(id: Int)
}
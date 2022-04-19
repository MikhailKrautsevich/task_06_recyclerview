package com.example.recyclerviewapp

import com.example.recyclerviewapp.data.ContactData

interface DataSupplier {
    fun getContacts(): ArrayList<ContactData>
}
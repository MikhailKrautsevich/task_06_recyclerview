package com.example.recyclerviewapp.data

import android.content.Context
import android.content.res.Resources
import com.example.recyclerviewapp.R
import kotlin.random.Random

class DataModel(context: Context) {
    private companion object {
        const val PHONE_SAMPLE = "+37529%d"
    }

    private val contactMap: MutableMap<Int, ContactData> = HashMap()
    private val res: Resources = context.resources
    private val nameArray: Array<String> = res.getStringArray(R.array.names)
    private val lastnameArray: Array<String> = res.getStringArray(R.array.lastnames)

    private var contactsCreated = 0

    init {
        for (i in 0..120)
            addRandomContact()
    }

    private fun addRandomContact() {
        val randomName = nameArray[Random.nextInt(nameArray.size)]
        val randomLastName = lastnameArray[Random.nextInt(lastnameArray.size)]
        val randomNumber =
            String.format(PHONE_SAMPLE, (Random.nextInt(8_999_999) + 1_000_000))
        val picId = getRandomPicId()

        val contact = ContactData(
            id = contactsCreated,
            name = randomName,
            lastname = randomLastName,
            number = randomNumber,
            picId = picId
        )
        contactMap.put(contact.id, contact)
        contactsCreated++
    }

    private fun getRandomPicId(): Int = Random.nextInt(1050) + 1

    fun getContacts(): ArrayList<ContactData> {
        val list = ArrayList<ContactData>()
        list.addAll(contactMap.values)
        return list
    }

    fun getContById(id: Int): ContactData? {
        return contactMap.get(id)
    }

    fun deleteContact(id: Int) {
        contactMap.remove(id)
    }
}
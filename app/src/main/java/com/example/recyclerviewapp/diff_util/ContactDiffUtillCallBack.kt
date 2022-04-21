package com.example.recyclerviewapp.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerviewapp.data.ContactData

class ContactDiffUtilCallBack(
    private val oldList: List<ContactData>,
    private val newList: List<ContactData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return ((oldItem.id) == (newItem.id))
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return (oldItem.number).equals(newItem.number)
                && (oldItem.name).equals(newItem.name)
                && (oldItem.lastname).equals(newItem.lastname)
                && (oldItem.picId).equals(newItem.picId)
    }
}
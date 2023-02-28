package com.example.recyclerviewapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerviewapp.model.Person

class DiffUtilsCallback(private val oldList: List<Person>, private val newList: List<Person>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson: Person = oldList[oldItemPosition]
        val newPerson: Person = newList[newItemPosition]
        return oldPerson.getInfo()[0] == newPerson.getInfo()[0]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
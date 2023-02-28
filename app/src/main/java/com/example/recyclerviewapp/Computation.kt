package com.example.recyclerviewapp

import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerviewapp.adapter.DiffUtilsCallback
import com.example.recyclerviewapp.adapter.PersonAdapter
import com.example.recyclerviewapp.model.Person
import com.example.recyclerviewapp.model.UserService

interface Computation {
    fun computeDeleteItem(
        userService: UserService,
        person: Person,
        list: List<Person>,
        adapter: PersonAdapter
    )

    class Base : Computation {
        override fun computeDeleteItem(
            userService: UserService,
            person: Person,
            list: List<Person>,
            adapter: PersonAdapter
        ) {
            val newList: MutableList<Person> = mutableListOf()
            newList.addAll(userService.returnList())
            newList.remove(person)

            val callback = DiffUtilsCallback(list, newList)
            val diff = DiffUtil.calculateDiff(callback)

            adapter.setData(newList)
            diff.dispatchUpdatesTo(adapter)
        }
    }

}
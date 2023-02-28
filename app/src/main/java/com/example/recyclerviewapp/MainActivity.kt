package com.example.recyclerviewapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.adapter.ClickListener
import com.example.recyclerviewapp.adapter.DiffUtilsCallback
import com.example.recyclerviewapp.adapter.PersonAdapter
import com.example.recyclerviewapp.databinding.ActivityMainBinding
import com.example.recyclerviewapp.model.Person
import com.example.recyclerviewapp.model.UserService
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PersonAdapter(object : ClickListener {
            override fun onClick(name: String) {
                Snackbar.make(binding.recyclerView, name, Snackbar.LENGTH_SHORT).show()
            }

            override fun onDelete(
                person: Person,
                list: MutableList<Person>,
                userService: UserService
            ) {
                val newList: MutableList<Person> = mutableListOf()
                newList.addAll(userService.returnList())
                newList.remove(person)
                Log.d("New List size", newList.size.toString())
                Log.d("Old List size", list.size.toString())
                val callback = DiffUtilsCallback(list, newList)
                val diff = DiffUtil.calculateDiff(callback)

                adapter.setData(newList)
                diff.dispatchUpdatesTo(adapter)
            }


        })
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

}

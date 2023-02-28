package com.example.recyclerviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.adapter.ClickListener
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
                Computation.Base().computeDeleteItem(userService, person, list, adapter)
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

}

package com.example.recyclerviewapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.ItemPersonBinding
import com.example.recyclerviewapp.model.App
import com.example.recyclerviewapp.model.Person
import com.example.recyclerviewapp.model.UserService

class PersonAdapter(
    clickListener: ClickListener
) :
    RecyclerView.Adapter<PersonViewHolder>() {
    private val userService = App().userService
    private var persons = userService.generatePersons()
    private val onClickListener = OnClickListener.Base(clickListener)

    fun setData(newList: MutableList<Person>) {
        persons.clear()
        persons.addAll(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount() = persons.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.onBind(persons, position)
        onClickListener.onViewClickListener(holder, persons[position])
        onClickListener.onDeleteClickListener(userService, holder, persons[position], persons)

    }

}

class PersonViewHolder(private val binding: ItemPersonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(list: List<Person>, position: Int) {
        val person = list[position]
        val params = person.getInfo()
        val name = params[0]
        val photo = params[1]
        binding.name.text = name
        if (photo.isNotBlank()) {
            Glide.with(binding.personsPhoto.context)
                .load(photo)
                .circleCrop()
                .error(R.drawable.ik_user_avatar)
                .placeholder(R.drawable.ik_user_avatar)
                .into(binding.personsPhoto)
        } else {
            binding.personsPhoto.setImageResource(R.drawable.ik_user_avatar)
        }
    }

}

interface OnClickListener {
    fun onViewClickListener(holder: PersonViewHolder, person: Person)
    fun onDeleteClickListener(
        userService: UserService,
        holder: PersonViewHolder,
        person: Person,
        personList: MutableList<Person>
    )

    class Base(private val clickListener: ClickListener) : OnClickListener {
        override fun onViewClickListener(holder: PersonViewHolder, person: Person) {
            holder.itemView.setOnClickListener {
                clickListener.onClick(person.getInfo()[0])
            }
        }

        override fun onDeleteClickListener(
            userService: UserService,
            holder: PersonViewHolder,
            person: Person,
            personList: MutableList<Person>
        ) {
            holder.itemView.findViewById<ImageView>(R.id.more_button).setOnClickListener {
                clickListener.onDelete(person, personList, userService)
            }

        }


    }
}

interface ClickListener {
    fun onClick(name: String)
    fun onDelete(person: Person, list: MutableList<Person>, userService: UserService)
}



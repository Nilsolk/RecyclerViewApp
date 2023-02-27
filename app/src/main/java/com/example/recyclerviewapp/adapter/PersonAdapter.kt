package com.example.recyclerviewapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.ItemPersonBinding
import com.example.recyclerviewapp.model.Person
import com.example.recyclerviewapp.model.UserService

class PersonAdapter(
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<PersonViewHolder>() {
    private val userService = UserService()
    private var persons = userService.generatePersons()

    private val onClickListener = OnClickListener.Base()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount() = persons.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]
        Log.d("bindvalue", persons.size.toString())

        holder.onBind(person)

        onClickListener.onViewClickListener(holder, clickListener, person)

        val moreButton = holder.itemView.findViewById<ImageView>(R.id.more_button)
        //onClickListener.onDeleteClickListener(moreButton, person, userService, this, persons)


        moreButton.setOnClickListener {
            val oldValue = persons
            Log.d("oldvalue", oldValue.size.toString())
            userService.removeUser(person)
            Log.d("newvalue", persons.size.toString())
            val diffUtilsCallback = DiffUtilsCallback(oldValue, persons)
            val diff = DiffUtil.calculateDiff(diffUtilsCallback)

            diff.dispatchUpdatesTo(this)


        }
    }

}

class PersonViewHolder(private val binding: ItemPersonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(person: Person) {
        val list = person.getInfo()
        val name = list[0]
        val photo = list[1]
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
    fun onViewClickListener(view: PersonViewHolder, callback: ClickListener, person: Person)
    fun onDeleteClickListener(
        view: ImageView,
        person: Person,
        userService: UserService,
        adapter: PersonAdapter,
        persons: MutableList<Person>
    )

    class Base : OnClickListener {
        override fun onViewClickListener(
            view: PersonViewHolder,
            callback: ClickListener,
            person: Person
        ) {
            view.itemView.setOnClickListener {
                callback.onClick(person.getInfo()[0])
            }
        }

        override fun onDeleteClickListener(
            view: ImageView,
            person: Person,
            userService: UserService,
            adapter: PersonAdapter,
            persons: MutableList<Person>
        ) {
            view.setOnClickListener {
                userService.removeUser(person)
            }

        }

    }
}

interface ClickListener {
    fun onClick(name: String)
}



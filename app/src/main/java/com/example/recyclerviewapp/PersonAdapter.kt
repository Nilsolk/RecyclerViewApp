package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.databinding.ItemPersonBinding
import com.example.recyclerviewapp.model.Person
import com.example.recyclerviewapp.model.UserService

class PersonAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<PersonViewHolder>() {
    private var users: List<Person> = UserService().returnPersons()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val user = users[position]
        holder.onBind(user)
        holder.itemView.setOnClickListener {
            clickListener.onClick(user.getInfo()[0])
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
                .centerCrop()
                .error(R.drawable.ik_user_avatar)
                .placeholder(R.drawable.ik_user_avatar)
                .into(binding.personsPhoto)
        } else {
            binding.personsPhoto.setImageResource(R.drawable.ik_user_avatar)
        }
    }
}

interface ClickListener {
    fun onClick(name: String)
}


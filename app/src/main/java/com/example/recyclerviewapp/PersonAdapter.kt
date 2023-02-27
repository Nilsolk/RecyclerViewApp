package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.databinding.ItemPersonBinding
import com.example.recyclerviewapp.model.Person
import com.example.recyclerviewapp.model.UserService

class PersonAdapter(
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<PersonViewHolder>() {
    private val userService = UserService()
    private val users = userService.returnPersons()

    private val onClickListener = OnClickListener.Base()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = users[position]
        holder.onBind(person)

        onClickListener.onViewClickListener(holder, clickListener, person)

        val moreButton = holder.itemView.findViewById<ImageView>(R.id.more_button)
        onClickListener.onMoreClickListener(moreButton, person, userService, this)
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
    fun onMoreClickListener(
        view: ImageView,
        person: Person,
        userService: UserService,
        adapter: PersonAdapter
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

        override fun onMoreClickListener(
            view: ImageView,
            person: Person,
            userService: UserService,
            adapter: PersonAdapter,
        ) {
            view.setOnClickListener {
                userService.removeUser(person)
                adapter.notifyDataSetChanged()

            }

        }

    }
}

interface ClickListener {
    fun onClick(name: String)
}



package com.example.recyclerviewapp.model

data class Person(
    private val id: Int,
    private val name: String,
    private val photo: String
) {
    fun getInfo(): List<String> {
        return listOf(name, photo)
    }
}
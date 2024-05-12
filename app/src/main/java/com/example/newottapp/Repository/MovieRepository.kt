package com.example.newottapp.Repository

import com.example.newottapp.models.Movie


interface MovieRepository {
    fun getList(position: Int) : ArrayList<Movie>
}
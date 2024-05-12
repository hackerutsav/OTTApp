package com.example.newottapp.Repository



import com.example.newottapp.models.Movie
import com.example.newottapp.ui.util.apiJson
import com.google.gson.Gson
import org.json.JSONObject


class MovieRepositoryImpl() : MovieRepository {

    //getting movie list by pages by parsing json data given in api
    //using gson to convert to model class
    override fun getList(position: Int): ArrayList<Movie> {
        val movieList = ArrayList<Movie>()
        val apiJson = apiJson()
        val json= JSONObject(apiJson.apiJson[position-1])
        val jsonArray = json.getJSONObject("page").getJSONObject("content-items").getJSONArray("content")
        for (i in 0 until jsonArray.length())
        {
            val gson = Gson()
            val thisObject = jsonArray.get(i).toString()
            movieList.add(gson.fromJson(thisObject, Movie::class.java))
        }
        return movieList
    }
}
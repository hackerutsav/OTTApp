package com.example.newottapp.viewmodels
import androidx.lifecycle.ViewModel
import com.example.newottapp.Repository.MovieRepository
import com.example.newottapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    //saves data during orientation change,has both current data and searched data
    var currentList:ArrayList<Movie> = arrayListOf()
    var searchText = ""
    fun getList(position:Int) : ArrayList<Movie> {
        val resultList  = movieRepository.getList(position)
        currentList.addAll(resultList)
        return resultList
    }

    fun getSearchList(text:String) : ArrayList<Movie>
    {
        return ArrayList(currentList.filter { it.name.contains(text, ignoreCase = true)})
    }
}
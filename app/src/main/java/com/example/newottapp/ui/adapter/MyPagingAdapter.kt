package com.arthlimchiu.basicdaggertutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newottapp.R
import com.example.newottapp.models.Movie
import com.paging.gridview.PagingBaseAdapter

//adapter which manages pagination and showing data in gridview

class MyPagingAdapter : PagingBaseAdapter<Movie>() {
    private lateinit var name: TextView
    private lateinit var movieImage: ImageView
    private lateinit var context:Context
    private var layoutInflater: LayoutInflater? = null

    fun setContext(contextin : Context)
    {
        context = contextin
    }

    override fun getCount(): Int {
        return items.size
    }



    override fun getItem(position: Int): Movie? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertNewView = convertView
        // on blow line we are checking if layout inflater
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertNewView == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertNewView = layoutInflater!!.inflate(R.layout.singleportaititem, null)
        }
        // on below line we are initializing our course image view
        // and course text view with their ids.
        name = convertNewView!!.findViewById(R.id.movieName)
        movieImage = convertNewView.findViewById(R.id.movieImage)
        // on below line we are setting text in our course text view.
        name.text = items[position].name
        val posterName:String = items[position].posterimage.substring(0, items[position].posterimage.indexOf('.'))

        val requestOptions = RequestOptions().error(R.drawable.placeholder_for_missing_posters)

        Glide.with(context)
            .load(getImage(posterName))
            .fitCenter()
            .apply(requestOptions)
            .into(movieImage)
        return convertNewView
    }

    private fun getImage(imageName: String?): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}
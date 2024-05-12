package com.example.newottapp.ui.view

import android.content.res.Configuration
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.arthlimchiu.basicdaggertutorial.MyPagingAdapter
import com.example.newottapp.R
import com.example.newottapp.viewmodels.MainViewModel
import com.paging.gridview.FooterViewGridAdapter
import com.paging.gridview.PagingGridView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var grid: PagingGridView
    private val viewModel: MainViewModel by viewModels()
    private var pager = 0

    private var currentpage=1;

    lateinit var gridadapter: MyPagingAdapter

    private var isSearch = false
    private var isBackEnabled = true
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.currentList = arrayListOf()
        grid = findViewById(R.id.moviesGrid)
        grid.setHasMoreItems(true)
        //setting pagination listener for pagination
        grid.setPagingableListener(PagingGridView.Pagingable {
            if (pager < 3 && currentpage < 3 && !isSearch) {
                currentpage++
                grid.onFinishLoading(
                    true, if (currentpage == 2)
                        viewModel.getList(2)
                    else
                        viewModel.getList(3)
                )
            } else {
                grid.onFinishLoading(false, null)
            }
        })

        setGridAdapter()
    }

    private fun setGridAdapter()
    {
        gridadapter = MyPagingAdapter()
        grid.adapter = gridadapter
        gridadapter.setContext(this@MainActivity)
        grid.onFinishLoading(true, viewModel.getList(1))
        supportActionBar?.title = resources.getString(R.string.movieType)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val d = ContextCompat.getDrawable(this, R.drawable.nav_bar)
        supportActionBar?.setBackgroundDrawable(d)
    }
    private fun clearData() {
        if (grid.adapter != null) {
            pager = 0
            gridadapter =
                (grid.adapter as FooterViewGridAdapter).wrappedAdapter as MyPagingAdapter
            gridadapter.removeAllItems()
            grid = findViewById<View>(R.id.moviesGrid) as PagingGridView
            gridadapter = MyPagingAdapter()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val myActionMenuItem: MenuItem? = menu?.findItem(R.id.searchUSer)
        searchView = myActionMenuItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.searchMovies)
        if (viewModel.searchText.length>2) {
            searchView.setQuery(viewModel.searchText, false)
            searchView.isIconified = false
            getSearchData(viewModel.searchText)
        }
        val et = searchView.findViewById<View>(searchView.context.resources
                .getIdentifier("android:id/search_src_text", null, null)) as EditText
        et.filters = arrayOf<InputFilter>(LengthFilter(10))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                isBackEnabled = true
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                isBackEnabled = false
                viewModel.searchText = newText.toString()
                if (TextUtils.isEmpty(newText)) {
                    isSearch = false
//                    adapter.filter("")
//                    listView.clearTextFilter()
                    clearData()
                    grid.onFinishLoading(true, viewModel.currentList)
                } else if (newText?.length!! >2)  {
                    getSearchData(newText)
//                    adapter.filter(newText)
                }
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.itemId
        if (id ==R.id.searchUSer) {
            // Do something
            return true
        }
        else if (id ==android.R.id.home) {
            if (isBackEnabled)
                finish()
            else {
                searchView.setQuery("", false)
                searchView.isIconified = true
                isBackEnabled = true
            }
            // Do something
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSearchData(searchData:String)
    {
        isSearch = true
        clearData()
        val newList = viewModel.getSearchList(searchData)
        viewModel.searchText = searchData
        clearData()
        grid.onFinishLoading(true, newList)
    }
}
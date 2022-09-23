package com.example.githubusersub2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.Adapter.OnItemClickCallback
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.Adapter.ListPersonAdapter
import com.example.githubusersub2.Detail.DetailActivity
import com.example.githubusersub2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val adapter: ListPersonAdapter by lazy {
        ListPersonAdapter()
    }
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showProgresBar(true)
    }

    private fun showProgresBar(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getSearchPerson(this@MainActivity, query)
                mainViewModel.searchPerson.observe(this@MainActivity){ searchPersonResponse ->
                    if (searchPersonResponse != null){
                        adapter.addDataList(searchPersonResponse)
                        setPersonData()
                    }

                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            private fun setPersonData() {
                val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.rvPerson.layoutManager = layoutManager
                binding.rvPerson.setHasFixedSize(true)
                binding.rvPerson.adapter = adapter
                adapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onItemClicked(person: PersonRespons) {
                        val i = Intent(this@MainActivity, DetailActivity::class.java)
                        i.putExtra(DetailActivity.EXTRA_PERSON, person)
                        startActivity(i)
                    }
                })
            }
        })
        return true
    }
}
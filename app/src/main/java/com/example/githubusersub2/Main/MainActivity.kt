package com.example.githubusersub2.Main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersub2.Adapter.ListPersonAdapter
import com.example.githubusersub2.Adapter.OnItemClickCallback
import com.example.githubusersub2.Detail.DetailActivity
import com.example.githubusersub2.Favorite.FavPerson
import com.example.githubusersub2.R
import com.example.githubusersub2.Response.PersonRespons
import com.example.githubusersub2.Setting.SettingMenuDarkMode
import com.example.githubusersub2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            mainViewModel.getPersonList(this)
        }
        initObserver()
    }

    private fun initObserver() {
        mainViewModel.apply {
            loading.observe(this@MainActivity) {
                showProgressBar(it)
            }

            person.observe(this@MainActivity) {
                if (it != null) {
                    setPersonData(it)
                }
            }

            error.observe(this@MainActivity) {

            }
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
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
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean{
        when(item.itemId){
            R.id.setting_btn_activity -> {
                val i = Intent(this, SettingMenuDarkMode::class.java)
                startActivity(i)
            }
            R.id.fav_btn_activity -> {
                val i = Intent(this, FavPerson::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setPersonData(data: ArrayList<PersonRespons>) {
        binding.rvPerson.apply {
            val linearLayoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            setHasFixedSize(true)

            val rvAdapter = ListPersonAdapter()
            rvAdapter.addDataList(data)
            rvAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(person: PersonRespons) {
                    val i = Intent(this@MainActivity, DetailActivity::class.java)
                    i.putExtra(DetailActivity.EXTRA_PERSON, person.login)
                    startActivity(i)
                }
            })

            adapter = rvAdapter
        }
    }

}
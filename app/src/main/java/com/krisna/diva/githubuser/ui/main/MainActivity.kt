package com.krisna.diva.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.githubuser.R
import com.krisna.diva.githubuser.data.remote.response.UsersItem
import com.krisna.diva.githubuser.databinding.ActivityMainBinding
import com.krisna.diva.githubuser.ui.BaseActivity
import com.krisna.diva.githubuser.ui.UserAdapter
import com.krisna.diva.githubuser.ui.favorite.FavoriteActivity
import com.krisna.diva.githubuser.ui.settings.SettingsActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        with(binding) {
            val layoutManager = LinearLayoutManager(root.context)
            rvUser.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(root.context, layoutManager.orientation)
            rvUser.addItemDecoration(itemDecoration)

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                mainViewModel.updateQuery(searchView.text.toString())
                searchBar.setText(searchView.text)
                searchView.hide()
                false
            }
        }

        mainViewModel.listUser.observe(this) {
            with(binding) {
                if (it.isNullOrEmpty()) {
                    tvNotFound.visibility = View.VISIBLE
                    rvUser.visibility = View.GONE
                } else {
                    tvNotFound.visibility = View.GONE
                    rvUser.visibility = View.VISIBLE
                    setUserData(it)
                }
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        mainViewModel.toastMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserData(userData: List<UsersItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userData)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
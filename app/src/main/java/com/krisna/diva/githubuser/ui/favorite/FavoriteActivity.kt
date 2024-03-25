package com.krisna.diva.githubuser.ui.favorite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.githubuser.R
import com.krisna.diva.githubuser.databinding.ActivityFavoriteBinding
import com.krisna.diva.githubuser.ui.BaseActivity
import com.krisna.diva.githubuser.ui.ViewModelFactory

class FavoriteActivity : BaseActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite)

        val favoriteViewModel = obtainViewModel(this)
        favoriteViewModel.getAllFavoriteUsers().observe(this) { favoriteUserList ->
            if (favoriteUserList != null) {
                adapter.setListFavoriteUsers(favoriteUserList)
                binding.tvEmpty.visibility = if (favoriteUserList.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        with(binding) {
            adapter = FavoriteUserAdapter(favoriteViewModel)
            val layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavoriteUser.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@FavoriteActivity, layoutManager.orientation)
            rvFavoriteUser.addItemDecoration(itemDecoration)
            rvFavoriteUser.setHasFixedSize(true)
            rvFavoriteUser.adapter = adapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}
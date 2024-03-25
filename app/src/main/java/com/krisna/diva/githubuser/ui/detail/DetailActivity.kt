package com.krisna.diva.githubuser.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.krisna.diva.githubuser.R
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity
import com.krisna.diva.githubuser.data.remote.response.DetailUserResponse
import com.krisna.diva.githubuser.databinding.ActivityDetailBinding
import com.krisna.diva.githubuser.ui.BaseActivity
import com.krisna.diva.githubuser.ui.ViewModelFactory
import com.krisna.diva.githubuser.ui.follow.FollowPagerAdapter
import com.krisna.diva.githubuser.utils.DateFormatter

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val detailViewModel = obtainViewModel(this)
        val userName = intent.getStringExtra(USERNAME)

        if (userName != null) {
            val followPagerAdapter = FollowPagerAdapter(this, userName)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = followPagerAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            detailViewModel.updatePath(userName)
        }

        detailViewModel.user.observe(this) { user ->
            setUserData(user)
            val favoriteUserEntity = FavoriteUserEntity(user.login, user.avatarUrl, DateFormatter.getCurrentDate())
            detailViewModel.get(user.login).observe(this) { favoriteUser ->
                if (favoriteUser != null) {
                    binding.floatingActionButton.setImageResource(R.drawable.outline_favorite_24)
                    binding.floatingActionButton.setOnClickListener {
                        detailViewModel.delete(favoriteUserEntity)
                    }
                } else {
                    binding.floatingActionButton.setImageResource(R.drawable.baseline_favorite_border_24)
                    binding.floatingActionButton.setOnClickListener {
                        detailViewModel.insert(favoriteUserEntity)
                    }
                }
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.toastMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserData(user: DetailUserResponse) {
        with(binding) {
            supportActionBar?.title = user.login
            tvUserName.text = user.login
            tvFullName.text = user.name
            tvFollowers.text = getString(R.string.followers, user.followers.toString())
            tvFollowing.text = getString(R.string.following, user.following.toString())
            Glide.with(root.context)
                .load(user.avatarUrl)
                .into(ivImage)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}
